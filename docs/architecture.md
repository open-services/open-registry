> This documentation describes the current infrastructure architecture we're
running in production. We're very happy to receive any feedback if you have it

## Infrastructure Architecture

Development is being made with a docker-compose file, where all services are
defined.

Currently we have 6 services, which are:

- app (open-services/open-registry)
- metrics (prometheus)
- ipfs (go-ipfs)
- front (caddy)
- dashboard (grafana)
- finance (open-services/finance-watch)
