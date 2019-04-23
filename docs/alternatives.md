There are plenty alternatives to Open-Registry, probably the most
famous is the `npm registry` by the company `npm inc`.

Most differences in the listed alternatives is about performance and governance.

For performance differences between the different registries, please look at
the benchmark results from [https://github.com/open-services/public-registry-benchmarks](https://github.com/open-services/public-registry-benchmarks)

Name | Governance | Geographic Location
------------ | ------------- | ------------
Open-Registry | Community Owned | EU
npm Registry | For-profit Company | US
node-modules.io Registry | Individual | US
cnpmjs.org Registry | Unclear | China?
js-ipfs Registry | For-profit Company | US

In general, it's easy to switch to any alternatives on this list. Simply
run `npm config set registry $registry-url` in your terminal, replacing `$registry-url`
with the URL to the registry.
