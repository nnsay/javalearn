FROM alpine:3.14 as downler

ARG VERSION=1.8.3
WORKDIR /build

RUN sed -i s/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g /etc/apk/repositories
RUN apk --update add curl jq &&  \
    wget $(curl https://api.github.com/repos/alibaba/Sentinel/releases/tags/${VERSION} | jq -r '.assets[0].browser_download_url')

FROM openjdk:11.0.8-jre-slim
ARG VERSION=1.8.3
COPY --from=downler /build/sentinel-dashboard-${VERSION}.jar /apps/sentinel-dashboard.jar
ENTRYPOINT ["java", "-jar", "/apps/sentinel-dashboard.jar"]
