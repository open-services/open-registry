# Federation

## Notice:
All these ideas here are fresh out in the wild. If you see something weird or
you just want to help out, reach out in a issue.

## Short Version

This proposed feature of Open-Registry opens up the ability to federate servers
for serving the tarballs for the registry.

## Motivation

Running a global network of the scale of the npm registry will be impossible
to do with just being funded by the community as the costs will be too high.

With Federation, the community can help with whatever resources they have available,
to make the Open-Registry faster, more resillient and stable.

A user (person installing modules) using the federated Open-Registry would
not notice it in their day-to-day usage as the registry continues to operate
as normal for them.

A organization using Open-Registry could easily install a federated Open-Registry
on-premise, saving bandwidth and time.

It would introduce a new class of contributors to the Open-Registry, named
"Registry Operators", who's responsibility is to make sure their registry
is operating normally and it's up-to-date.

## Security

We need to keep the following in mind while designing and running it:

- Malicious people might try to be a part of federation honestly, until they aren't honest anymore
- Constant monitoring needs to be applied to make sure servers are not in a performance or otherwise degraded state

### MVP Federation

MVP for having Federation for Open-Registry is having the following features:

- Metadata centralized with Open-Registry (npm.open-registry.dev)
- Tarball hosting can be anywhere as they are checksummed when downloading

Have a dynamic list of hosts to use

Have their distance between two IPs be calculated

Make it possible to chose the Tarball host with least hops

## Problems

- Lockfiles contains direct URLs to the registries
- New federated.open-registry.dev domain for this?
  - npm.open-registry.dev could write federated.open-registry.dev to the lockfiles
  - ^ probably not a good solution, federated.open-registry.dev should write its
      domain and npm.open-registry.dev its domain.

## Protocol

A Open-Registry wanting to participate in the federation of packages, needs to
run a Open-Registry service, pointing a change-feed to the centralized service.

Work towards a fully decentralized version will be made in the future.

The main Open-Registry node contains a root-hash that is what the registry index
is.

Every time this changes, Open-Registry will publish the new hash, and other
federated instances can pick it up at will.

## Rechecking

Each hour, Open-Registry will perform checks to see if the performance and the
results of the federated servers are actually doing what they should.

The risk for federated servers to serve bogus data is minimal, as both npm
and yarn checks the checksums of packages when fetching them. The metadata
is still served from a trusted server, so the checksums are trusted.

## Locations of Federated Servers (DNSimple DNS routing)

Currently, the centralized service runs at npm.open-registry.dev which is located
in Germany and servers the European community.

Federated servers are currently accepted in the following regions:

- US West-Coast
- US Central
- US East-Coast
- Africa
- South-America
- Asia
- Australia

If you're willing to host a server for Open-Registry, please open a issue in
open-services/open-registry-federation with your application.

## DNS/HTTP Routing

There are a couple of options we can explore for doing the federation.

The first option is to have one subdomain responsible for routing to the closest
server. We would use DNSimple to decide which region maps to which server.

Second option would be to do the routing with redirects in the centralized
server. This would be slower than the DNS option has requests would have to
hit the routing server before being redirected
