package forge.gamemodes.aisimulation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Stream;

public final class ArenaFingerprint {
    private ArenaFingerprint() { }

    public static String calculate(final List<Path> roots) throws IOException {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(exception);
        }
        for (int index = 0; index < roots.size(); index++) {
            final Path root = roots.get(index);
            update(digest, Integer.toString(index));
            if (!Files.exists(root)) {
                update(digest, "missing");
                continue;
            }
            if (Files.isDirectory(root)) {
                try (Stream<Path> files = Files.walk(root)) {
                    for (final Path path : files.filter(Files::isRegularFile).sorted().toList()) {
                        update(digest, root.relativize(path).toString().replace('\\', '/'));
                        hashFile(digest, path);
                    }
                }
            } else {
                hashFile(digest, root);
            }
        }
        return HexFormat.of().formatHex(digest.digest());
    }

    private static void update(final MessageDigest digest, final String value) {
        digest.update(value.getBytes(StandardCharsets.UTF_8));
        digest.update((byte) 0);
    }

    private static void hashFile(final MessageDigest digest, final Path path) throws IOException {
        update(digest, Long.toString(Files.size(path)));
        try (InputStream input = Files.newInputStream(path)) {
            final byte[] buffer = new byte[32768];
            int count;
            while ((count = input.read(buffer)) != -1) {
                digest.update(buffer, 0, count);
            }
        }
    }
}
