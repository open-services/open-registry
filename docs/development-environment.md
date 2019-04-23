## Development Environment

Open-Registry is setup for interactive development sessions.

This involves setting up your editor to connect to a running version of the
application and making changes directly to it.

The full environment can be setup by doing `docker-compose up`. This requires
both docker and docker-compose to work properly.

The nrepl will be running at the clojure containers port `33333` and it uses
the following startup command:

```
lein repl :headless :start :host 0.0.0.0 :port 33333
```

Get the IP of the container with the following command:

```
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i clojure | cut -d ' ' -f 1)
```

```
172.24.0.4 open-registry.dev docs.open-registry.dev npm.open-registry.dev metrics.open-registry.dev ipfs.open-registry.dev docs.open-registry.dev dashboard.open-registry.dev
```
