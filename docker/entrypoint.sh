#!/bin/bash
set -euo pipefail

if [[ ! "$SCREEN_RESOLUTION" =~ ^[1-9][0-9]{2,3}x[1-9][0-9]{2,3}$ ]]; then
    echo "SCREEN_RESOLUTION must be WIDTHxHEIGHT, for example 1600x900." >&2
    exit 1
fi

child_pids=()
shutdown() {
    trap - EXIT TERM INT
    if (( ${#child_pids[@]} )); then
        kill "${child_pids[@]}" 2>/dev/null || true
        wait "${child_pids[@]}" 2>/dev/null || true
    fi
}
trap shutdown EXIT
trap 'exit 0' TERM INT

Xvfb "$DISPLAY" -screen 0 "${SCREEN_RESOLUTION}x24" -nolisten tcp &
child_pids+=("$!")

display_ready=false
for attempt in {1..100}; do
    if xdpyinfo >/dev/null 2>&1; then
        display_ready=true
        break
    fi
    sleep 0.1
done
if [[ "$display_ready" != true ]]; then
    echo "The virtual display failed to start." >&2
    exit 1
fi

openbox --sm-disable &
child_pids+=("$!")
x11vnc -display "$DISPLAY" -rfbport 5900 -localhost -forever -shared -nopw -noxdamage &
child_pids+=("$!")
websockify --web=/usr/share/novnc/ 6080 localhost:5900 &
child_pids+=("$!")
java @/opt/forge/java.options \
    -Dio.netty.tryReflectionSetAccessible=true \
    -Dfile.encoding=UTF-8 \
    -cp '/opt/forge/lib/*' forge.view.Main &
child_pids+=("$!")

wait -n "${child_pids[@]}"
