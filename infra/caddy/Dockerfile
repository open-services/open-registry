FROM scratch
COPY bin/caddy /
COPY Caddyfile /
COPY Caddyfile.production /
CMD ["/caddy", "-conf", "Caddyfile"]
