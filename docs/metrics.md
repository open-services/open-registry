Metrics are collected in 15 second intervals from all services running in
connection with Open-Registry.

The point of these metrics is to be able to see the growth of the registry, any
pain points in the infrastructure and inspect any failures.

The metrics include everything from events happening in running applications,
commits landing in master, votes, finance and so on.

Each collected metric should have a purpose to be collected, that will benefit
the user. If we can skip the metric without loosing the ability to build something
great for our users, then we should skip it.

# Public Dashboard

The public dashboard is viewable by anyone and can be accessed here:
https://dashboard.open-registry.dev/d/7SmAxSzZz/general?orgId=1&from=now-15m&to=now&refresh=5s

# Changing the Dashboard

After started the application stack with `docker-compose up`, the front
will forward requests from `http://dashboard.open-registry.test` to the Grafana
dashboard. For this to work, you'll need to add a host entry in your `/etc/hosts`
pointing the Front IP to the domains you want to use. There is a helper script
in `infra/set-front-ip-to-hosts.sh` that does this for you.

(Note: this script will ask for your sudo password as it changes a normally
read-only file. Make sure to inspect the script before running and understand
what it does)

As the dashboard in production uses data from production, it might be a hassle
to have to guess what values it would be once deployed. To make it easier, you
can point your local grafana instance to the production prometheus instance,
meaning you'll have the exact same dashboard data in development, as we're using
in production.

To achieve this, you'll have to change the `datasources.url` value in
`infra/grafana/datasources/datasource.yml` from `http://metrics:9090` to
`https://metrics.open-registry.dev`. Now after doing `docker-compose up`,
Grafana will start ingesting data from the production instances.
