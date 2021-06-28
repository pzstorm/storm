FROM ruby:2.7.3-alpine3.13

LABEL maintainer="ferrari.marco@gmail.com"

SHELL ["/bin/ash", "-eo", "pipefail", "-c"]

RUN apk add --no-cache \
  git=2.30.2-r0

WORKDIR /
COPY Gemfile Gemfile
RUN apk add --no-cache \
  --virtual .gem-installdeps \
  build-base=0.5-r2 \
  && gem install bundler --version 2.2.15 \
  && bundle config set --local system 'true' \
  && bundle install \
  && gem uninstall bundler \
  && rm Gemfile Gemfile.lock \
  && rm -rf "$GEM_HOME"/cache \
  && apk del .gem-installdeps

ENV SRC_PATH /usr/local/src/storm
RUN mkdir -p "${SRC_PATH}"

VOLUME [ "$SRC_PATH" ]
WORKDIR $SRC_PATH

ENTRYPOINT ["github_changelog_generator"]
CMD ["--user", "pzstorm", "--project", "storm"]