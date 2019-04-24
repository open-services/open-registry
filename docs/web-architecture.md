# Web Architecture

The current architecture of Open-Registry looks something like this:

- open-registry.dev - landing page
  - served via dnslink
  - CNAME to ipfs.open-registry.dev where IPFS Gateway runs
  - same for docs.open-registry.dev

- npm.open-registry.dev - npm registry
  - points to npm registry application
  - application uses ipfs node to store cache

- ipfs.open-registry.dev - IPFS HTTP Gateway
  - serves websites
  - should only serve whitelisted content

- metrics.open-registry.dev - Prometheus Metrics Aggregator
  - aggregates metrics from different applications

- dashboard.open-registry.dev - Grafana Public Dashboard
  - publically accessible dashboard
