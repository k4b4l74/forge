FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build
COPY pom.xml checkstyle.xml ./
COPY .mvn/ .mvn/
COPY forge-core/ forge-core/
COPY forge-game/ forge-game/
COPY forge-ai/ forge-ai/
COPY forge-gui/pom.xml forge-gui/pom.xml
COPY forge-gui/src/ forge-gui/src/
COPY forge-gui-desktop/ forge-gui-desktop/
COPY forge-gui-mobile/pom.xml forge-gui-mobile/pom.xml
COPY forge-gui-mobile-dev/pom.xml forge-gui-mobile-dev/pom.xml
COPY forge-gui-android/pom.xml forge-gui-android/pom.xml
COPY forge-gui-ios/pom.xml forge-gui-ios/pom.xml
COPY forge-lda/pom.xml forge-lda/pom.xml
COPY adventure-editor/pom.xml adventure-editor/pom.xml
COPY forge-installer/pom.xml forge-installer/pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp -pl forge-gui-desktop -am \
        compile jar:jar dependency:copy-dependencies \
        -DincludeScope=runtime \
    && mkdir -p /opt/forge/lib \
    && cp forge-gui-desktop/target/dependency/*.jar /opt/forge/lib/ \
    && cp forge-gui-desktop/target/forge-gui-desktop-*.jar /opt/forge/lib/ \
    && sed -n 's:.*<addopen.java.args>\(.*\)</addopen.java.args>.*:\1:p' \
        forge-gui-desktop/pom.xml > /opt/forge/java.options \
    && test -s /opt/forge/java.options

FROM eclipse-temurin:21-jre-jammy AS runtime

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        curl fonts-dejavu-core libasound2 libxi6 libxrender1 libxtst6 \
        novnc openbox websockify x11-utils x11vnc xvfb \
    && rm -rf /var/lib/apt/lists/* \
    && useradd --create-home --uid 1000 --shell /bin/bash forge \
    && mkdir -p /home/forge/.forge /home/forge/.cache/forge \
    && chown -R forge:forge /home/forge

COPY --from=build /opt/forge/ /opt/forge/
COPY forge-gui/res/ /opt/forge/forge-gui/res/
COPY forge-gui/forge.profile.properties.example /opt/forge/forge-gui/
COPY LICENSE /opt/forge/LICENSE
COPY --chmod=755 docker/entrypoint.sh /usr/local/bin/forge-desktop

ENV DISPLAY=:99 \
    SCREEN_RESOLUTION=1600x900 \
    JAVA_TOOL_OPTIONS="-Xms512m -Xmx4g"

USER forge
WORKDIR /opt/forge/forge-gui-desktop
EXPOSE 6080
HEALTHCHECK --interval=30s --timeout=5s --start-period=90s --retries=3 \
    CMD curl --fail --silent http://127.0.0.1:6080/vnc.html >/dev/null && xdpyinfo >/dev/null 2>&1
ENTRYPOINT ["/usr/local/bin/forge-desktop"]
