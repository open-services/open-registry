FROM debian:stable
COPY bin/caddy /
COPY Caddyfile /
COPY Caddyfile.production /
CMD ["/caddy", "-conf", "Caddyfile"]
