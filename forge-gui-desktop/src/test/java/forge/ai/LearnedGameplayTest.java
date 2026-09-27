package forge.ai;

import com.sun.net.httpserver.HttpServer;
import forge.game.Game;
import forge.game.card.CardCollection;
import forge.game.player.Player;
import forge.game.zone.ZoneType;
import forge.view.ArenaLearningSession;
import org.testng.annotations.Test;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.expectThrows;

public class LearnedGameplayTest extends AITest {
    @Test(timeOut = 180000)
    public void appliesValidatedModelChoicesAndAuditsFallbacksWithoutSendingTeacherLabels() throws Exception {
        final Game game = initAndCreateGame();
        final Player player = game.getPlayers().get(1);
        ((LobbyPlayerAi) player.getLobbyPlayer()).setAiProfile("OS Reanimator v2");
        final CardCollection cards = new CardCollection();
        for (final String name : List.of("Bayou", "Underground Sea", "Animate Dead", "Triskelion", "Copy Artifact")) {
            cards.add(addCardToZone(name, player, ZoneType.Hand));
        }
        final CardCollection teacher = OldschoolReanimatorAi.discard(player, cards, 1);
        final String sha = "a".repeat(64);
        final AtomicReference<String> response = new AtomicReference<>("{\"schema\":1,\"modelSha256\":\"" + sha + "\",\"action\":4,\"value\":0.2}");
        final AtomicReference<String> request = new AtomicReference<>();
        final AtomicInteger requests = new AtomicInteger();
        final HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        final Path directory = Files.createTempDirectory("learned-gameplay-");
        server.createContext("/choose", exchange -> {
            requests.incrementAndGet();
            request.set(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
            final byte[] bytes = response.get().getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (var output = exchange.getResponseBody()) { output.write(bytes); }
        });
        server.start();
        try {
            final var settings = new ArenaLearningSession.Settings(1, "infer", List.of("OS Reanimator v2"),
                    "http://127.0.0.1:" + server.getAddress().getPort() + "/choose", sha, 1000);
            try (var session = new ArenaLearningSession(directory, settings, game, 0, 1, 123)) {
                OldschoolReanimatorAi.keepHand(player, 0);
                assertEquals(requests.get(), 0);
                assertEquals(OldschoolReanimatorAi.discard(player, cards, 1).getFirst().getName(), "Copy Artifact");
                assertFalse(request.get().contains("teacher"));
                assertFalse(request.get().contains("options"));
                response.set("{\"schema\":1,\"modelSha256\":\"" + sha + "\",\"action\":500,\"value\":0}");
                assertEquals(OldschoolReanimatorAi.discard(player, cards, 1), teacher);
                response.set("{\"schema\":1,\"modelSha256\":\"wrong-model\",\"action\":0,\"value\":0}");
                assertEquals(OldschoolReanimatorAi.discard(player, cards, 1), teacher);
                response.set("{}");
                assertEquals(OldschoolReanimatorAi.discard(player, cards, 1), teacher);
                assertEquals(OldschoolReanimatorAi.discard(player, cards, 1), teacher);
                assertEquals(requests.get(), 4);
            }
            assertEquals(OldschoolReanimatorAi.discard(player, cards, 1), teacher);
            try (Stream<Path> paths = Files.list(directory.resolve("learning"))) {
                final String log = Files.readString(paths.findFirst().orElseThrow());
                assertTrue(log.contains("\"reason\":\"model\""));
                assertTrue(log.contains("circuit-open"));
            }
        } finally {
            server.stop(0);
            try (Stream<Path> paths = Files.walk(directory)) {
                for (final Path path : paths.sorted(Comparator.reverseOrder()).toList()) { Files.deleteIfExists(path); }
            }
        }
    }

    @Test
    public void rejectsExternalEndpointsAndInvalidSettings() {
        for (final String endpoint : List.of("http://example.com/choose", "https://127.0.0.1/choose",
                "http://user@127.0.0.1/choose", "http://127.0.0.1/redirect", "http://127.0.0.1/choose?next=remote")) {
            expectThrows(IllegalArgumentException.class, () -> new ArenaLearningSession.Settings(1, "infer",
                    List.of("OS Reanimator v2"), endpoint, "a".repeat(64), 1000));
        }
        expectThrows(IllegalArgumentException.class, () -> new ArenaLearningSession.Settings(1, "infer",
                List.of("OS Reanimator v2"), "http://127.0.0.1/choose", "unpinned", 1000));
    }
}
