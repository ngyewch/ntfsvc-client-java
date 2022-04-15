VERSION --explicit-global 0.6
FROM debian:stretch
RUN apt-get -y update && apt-get install -y curl git
RUN git clone https://github.com/asdf-vm/asdf.git ~/.asdf --branch v0.9.0
RUN echo ". $HOME/.asdf/asdf.sh" >> /root/.bashrc
ENV PATH="${PATH}:/root/.asdf/shims:/root/.asdf/bin"

RUN asdf plugin add java

WORKDIR /workspace

setup:
    COPY .tool-versions .
    RUN asdf install
    COPY --dir gradle .
    COPY gradlew .
    RUN --mount=type=cache,target=/root/.gradle/caches \
        ./gradlew

build:
    FROM +setup
    COPY build.gradle.kts settings.gradle.kts .
    RUN --mount=type=cache,target=/root/.gradle/caches \
        ./gradlew
    COPY . .
    RUN --mount=type=cache,target=/root/.gradle/caches \
        ./gradlew build
