FROM clojure:openjdk-8-lein

WORKDIR /app

COPY . /app

RUN lein deps

CMD lein run
