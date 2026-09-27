# Run this checkout with Java 21

The root `Dockerfile` builds the Swing desktop client from this checkout using
Maven and Eclipse Temurin Java 21. The runtime also uses Java 21. Forge's Java
source compatibility remains 17, as configured by the project POM.

The container runs the graphical application on a virtual display, with
[noVNC](https://novnc.com/noVNC/) providing browser access. No Windows Java,
Maven, or X server installation is needed. This setup runs the Swing client;
Adventure/libGDX and browser audio are not included.

## Start from PowerShell with Docker in WSL

Open PowerShell in the repository root. A local `.env` contains the settings;
on a fresh clone, create it with `Copy-Item .env.example .env`.

```powershell
wsl.exe --exec docker compose up --build
```

WSL uses the current Windows directory through its mounted drive. If needed,
select your distribution explicitly with `wsl.exe -d Ubuntu --exec ...`.
From a Linux/WSL shell in the project directory, omit `wsl.exe --exec`.

Keep this terminal open while playing; press Ctrl+C to stop. With Docker Engine
installed inside WSL, a detached `up -d` command can leave WSL idle and cause the
distribution and containers to shut down shortly afterward. Microsoft notes that
[systemd services do not keep WSL alive](https://learn.microsoft.com/en-us/windows/wsl/systemd).
Foreground Compose keeps an active WSL session. Use detached mode only if another
active WSL session or your Docker installation keeps the daemon running.

The first build downloads Java/Maven images, Linux packages, and Maven
dependencies, and compiles Forge. Later builds reuse Docker layers and a Maven
cache. The image build compiles the application; it does not run the test suite.

Open [Forge in your browser](http://localhost:6080/vnc.html?autoconnect=true&resize=scale).
Initial card database loading can take a little time. If you change `FORGE_PORT`,
use that port in the URL. With a native WSL Docker daemon, Windows access depends
on WSL localhost forwarding being enabled.

## Configuration

Compose reads the root `.env` for
[variable interpolation](https://docs.docker.com/compose/how-tos/environment-variables/variable-interpolation/).
The local file is ignored by Git and excluded from the Docker build context;
`.env.example` supplies shareable defaults. Shell variables can override `.env`.

| Variable | Default | Meaning |
| --- | --- | --- |
| `COMPOSE_PROJECT_NAME` | `forge-local` | Compose project name. |
| `FORGE_DATA_DIR` | `./data` | Host directory for the profile and legacy cache, relative to the repository root. With WSL, absolute paths must use Linux syntax, such as `/mnt/d/ForgeData`. |
| `FORGE_CACHE_SOURCE` | `forge-cache-linux` in `.env.example` | Linux-backed Docker volume for downloads. An explicit host path, such as `./data/cache`, also works. If unset, Compose retains the legacy `${FORGE_DATA_DIR}/cache` bind mount. Migrate existing downloads before switching. |
| `FORGE_PORT` | `6080` | Browser port, bound to `127.0.0.1` only. |
| `SCREEN_RESOLUTION` | `1600x900` | Virtual desktop size, in `WIDTHxHEIGHT` form. |
| `VNC_USE_XDAMAGE` | `true` | Use X DAMAGE hints to detect screen changes faster. Set `false` if repaint artifacts occur. |
| `JAVA_MIN_HEAP` | `512m` | Initial Java heap. |
| `JAVA_MAX_HEAP` | `4g` | Maximum Java heap; leave additional WSL RAM for the desktop and JVM overhead. |
| `TZ` | `Europe/Paris` | Container timezone. |

After changing `.env`, stop and run `wsl.exe --exec docker compose up` to recreate the
container as needed. The browser endpoint has no password and is intended for
local use; keep the localhost port binding. The raw VNC port is not published.

## Data and daily commands

User data survives container rebuilds/recreation. With `.env.example` settings:

- `data/profile/`: preferences, decks, quest progress, and other user data;
  mounted at `/home/forge/.forge` inside the container.
- The `forge-cache-linux` Docker volume holds downloaded image indexes, card
  pictures, and other cached content at `/home/forge/.cache/forge`. For the default
  project name, its full name is `forge-local_forge-cache-linux`. Card pictures go
  under `pics/cards/`. This volume lives in Docker's Linux filesystem, avoiding
  repeated small-file reads across the Windows/WSL filesystem boundary.

The first-run image download prompt writes into this cache. Bundled game assets
remain in the image. Compose creates the profile directory and cache volume.
Back up both your profile and cache to retain saves and downloads. On Linux,
host directories must be writable by container UID 1000.

Existing `.env` files without `FORGE_CACHE_SOURCE` continue using `data/cache/`
(or `${FORGE_DATA_DIR}/cache`). This prevents an upgrade from silently hiding
existing downloads. To keep both profile and cache directly accessible in Windows
Explorer, set `FORGE_CACHE_SOURCE=./data/cache`, accepting the slower filesystem
access from WSL.

### Move an existing Windows cache to Linux storage

Save your work and stop Forge first. Set `FORGE_CACHE_SOURCE=forge-cache-linux`
in `.env`, but do not start Forge yet. Check `docker volume ls`: the destination
`<project>_forge-cache-linux` should be new/empty. If it already contains data,
back it up and resolve the conflict before copying; do not overwrite it blindly.
Keep your old `data/cache/` folder intact as a rollback copy.

From PowerShell in the repository root, with the default `FORGE_DATA_DIR`:

```powershell
wsl.exe --exec docker compose stop
wsl.exe --exec docker compose build forge
wsl.exe --exec docker compose run --rm --no-deps --volume ./data/cache:/cache-source:ro --entrypoint cp forge -a --no-preserve=ownership /cache-source/. /home/forge/.cache/forge/
```

Use your actual cache source path in `--volume` if `FORGE_DATA_DIR` differs.
The copy reads the old cache without modifying it and runs as Forge's UID 1000,
so the new files remain writable. Check that the copy exits successfully before
starting `wsl.exe --exec docker compose up`. Decks and preferences stay in the
existing profile bind mount. Migration is unnecessary for a fresh installation.

To export the Linux cache to a new/empty host backup directory while Forge is
stopped (using the default container name):

```powershell
New-Item -ItemType Directory data/cache-backup
wsl.exe --exec docker cp forge-local-forge-1:/home/forge/.cache/forge/. ./data/cache-backup/
```

For rollback, stop Forge, export any new downloads, and set `FORGE_CACHE_SOURCE`
back to `./data/cache` before recreating the container. The original Windows cache
is not synchronized with the Linux volume after migration.

### Daily commands

```powershell
wsl.exe --exec docker compose ps
wsl.exe --exec docker compose logs -f forge
wsl.exe --exec docker compose exec forge java -version
wsl.exe --exec docker compose stop
wsl.exe --exec docker compose up
```

Closing Forge's window stops the container. Run `docker compose up` again to
reopen it. Use `docker compose up --build` after source or resource changes;
the image contains a copy of the checkout, not a live source mount. For debugging
and rapid Java iteration, the IntelliJ run configurations remain useful.

`docker compose down` removes the container/network and retains saved data.
**Do not use `docker compose down --volumes` unless you intend to delete the
Linux cache and its downloaded images.** It does not delete bind-mounted host
folders, but deleting `data/` manually deletes the profile and any legacy cache.

## Display responsiveness

X DAMAGE is enabled by default. If parts of the screen stop repainting, set
`VNC_USE_XDAMAGE=false` and recreate the container to restore scan-based detection.
Keep the browser URL's `resize=scale` setting for fitting the desktop to the
window; this scales locally, not the virtual display. To reduce rendering and
transfer work, try `SCREEN_RESOLUTION=1280x720` instead of `1600x900` (36% fewer
pixels, with less desktop space). No extra Java heap is required for these changes.

## Implementation

The build selects `forge-gui-desktop` and its reactor dependencies. It invokes
`compile`, `jar:jar`, and `dependency:copy-dependencies` to assemble a runtime
classpath without the Windows launcher or release changelog packaging steps.
The unversioned jar manifests preserve Forge's development asset lookup, so the
working directory and `../forge-gui/res` layout intentionally match an IDE run.
Java module-opening options are extracted from the desktop POM at build time.
The runtime runs as the unprivileged `forge` user.

# Existing community Docker images

The following older instructions describe third-party images rather than the
source build above.

# FORGE-DESKTOP-SNAPSHOT

Pull docker image.
```
- docker pull xanxerdocker/forge-desktop-snapshot
```

Run container and remove container after exit:
```  
- docker run --rm -it -p 3002:3000 forge-desktop-snapshot bash
```

Run container in detached mode. Will retain data after exit:
```
- docker run -d -it -p 3002:3000 forge-desktop-snapshot bash
```
Access container at localhost:3002 in your web browser. 

Once inside the container. Run the setup script using sudo:
```
- bash setup_forge_desktop.sh
```
After the setup is complete. To play forge-desktop-SNAPSHOT or forge-adventure mode run: 
```
- bash forge_game_selector.sh
```

# FORGE DEV ENVIRONMENT:
Dockerized apps for a forge development environment
- intellij-ce
- Magic Set Editor 2 - Advanced
- Tiled - Map Editor

Pull docker images.
```
- docker pull xanxerdocker/intellij-ce-ide
- docker pull xanxerdocker/magic-set-editor-2-advanced
- docker pull xanxerdocker/tiled-map-editor
```
# Intellij-ce IDE container

Run Intellij-ce  container and remove container after exit:
```
- docker run --rm -it -p 3003:3000 xanxerdocker/intellij-cd-ide bash
```
Run detached Intellij-ce container. Will retain data after exit")
```
- docker run -d -it -p 3003:3000 xanxerdocker/intellij-ce-ide bash
```
Access container via localhost:3003 in web browser and to start application run:
```
- bash run_intellij-ce.sh
```


# Magic Set Editor 2 Advanced

Run Magic Set Editor 2 - Advanced container and remove container after exit.
```
- docker run --rm -it -p 3004:3000 xanxerdocker/magic-set-editor-2-advanced bash
```

Run detached: Magic Set Editor 2 - Advanced container. Will retain data after exit.
```
- docker run -d -it -p 3004:3000 xanxerdocker/magic-set-editor-2-advanced bash
```

Access container via localhost:3004 in web browser.

Once inside the container to compile the app and finish setup run using sudo:
```
- bash install-mse-adv-full.sh
```

After the app is compiled, to start app run:
```
- bash run_mse.sh
```

# Tiled - Map Editor

Run Tiled - Map Editor container and remove container after exit:
```
- docker run --rm -it -p 3005:3000 xanxerdocker/tiled-map-editor bash
```

Run detached Tiled - Map Editor container. Will retain data after exit.
```
- docker run -d -it -p 3005:3000 xanxerdocker/tiled-map-editor bash
```

Access container via localhost:3005 in web browser and to start application run:
```
- bash run_tiled_map_editor.sh
```
