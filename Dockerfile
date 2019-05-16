FROM clojure:openjdk-8-lein

WORKDIR /app

COPY project.clj /app/project.clj

RUN lein deps

COPY . /app

CMD lein run
