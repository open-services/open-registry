> This documentation describes the current infrastructure architecture we're
running in production. We're very happy to receive any feedback if you have it

## Current Infrastructure

Currently, we're running a built jar file built from open-services/open-registry
and downloaded caddy binary with the systemd files provided in `infra/contrib/`

## Future Infrastructure

Development is being made with a docker-compose file, where all services are
defined.

What's missing currently is using this docker-compose file to deploy the services.

Currently we have 6 services, which are:

- app (open-services/open-registry)
- metrics (prometheus)
- ipfs (go-ipfs)
- front (caddy)
- dashboard (grafana)
- finance (open-services/finance-watch)

We should deploy these services on two different hosts:

