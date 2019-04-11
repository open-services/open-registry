## Open-Registry - Open JavaScript Module Registry

Notice: This project is NOT ready to be shared + used yet. It's development just
got started and right now we're working on setting up the governance properly

https://open-registry.dev

> A user-focused registry of JavaScript libraries used, organized and funded by the community

### Features of Open-Registry

- Serves a mirror of the npm Registry (npm.open-registry.dev)
- Community funded
- Governed by the community
- Sole focus on providing a library registry

### Why Open-Registry?

Rather than relying on private, for-profit companies to host all of our
programing libraries (such as npm Inc hosting the npm Registry), we should
move towards a different model where the stability and durability of the registry
does not depend on a company but the community who is the user of the registry.

If we as a community can come together and fund our own registry, we'll
have full control of what the registry should be and how long we can make it
last.

The initial idea is to setup a full mirror of npm  and serve it to the public.

All the metrics will be public and viewable by anyone, and same with the data
in the registry itself.

This will of course need to be paid for, which will also be public. All the
payments and bills that will occur because of Open-Registry, will be publicly
trackable, and also the income which will be by donations.

So, it's time for developers to put their money where their mouth is, and start
prove that a self-organizing community can self-care about it's needs.

### Future goals

- Cryptographically signed packages
- Decentralized hosting where many operators can contribute to hosting securely
- Aims to be adopted by the OpenJS Foundation (or similar)
  - OpenJS = merge of the Node.js Foundation and the JS Foundation
- At least as fast as the npm registry in four key regions (Europe, North + South America and Asia)
- Verified modules (auto+manually inspected modules)

### Finance Interface

The finance interface has to be connected to different services to figure out
where we are at with the application.

- Paypal - To list incoming donations
- Stripe - -11-
- OpenCollective - -11-
- Hetzner - To show costs
- DNSimple - To show costs
- Gandi - To show costs

### Metrics Interface

What would you want to know?

- Number of instances for hosting
- How much the organization costs in total
- Where does that cost go to exactly
- How much is donated right now
- How much of that has been spent
- On what has that been spent?
- How many errors are happening currently?
- How many packages is the registry serving currently?
- How many of those are cached?
- How far back are we from the npm registry?

### Roadmap to MVP

- Waiting for GitHub Organization
- Ability to install packages from tested matrix of projects when installing deps
- See expected/actual income per month
- See expected/actual expense per month
- Governance

### Current High-Priority Issues

- Setup Infrastructure
- Publish readable documentation
- Finish governance and contributing guidelines
- Publish metrics
- Solve concurrency issue when two identical packages are being installed
  - Locks?

### Some Handy Links

- https://baremetrics.com/open-startups
- https://en.wikipedia.org/wiki/Public_utility
- https://en.wikipedia.org/wiki/Public_service
- https://en.wikipedia.org/wiki/Dead_man%27s_switch
- https://docs.opencollective.com/help/developers/api
- https://medium.com/open-collective/open-collective-graphql-api-preview-3b42ed1d55ff
- https://graphql-docs-v2.opencollective.com/account.doc
- http://www.apache.org/foundation/how-it-works.html#meritocracy
- https://opensource.guide/leadership-and-governance/
- https://status.npmjs.org/
- https://github.com/open-services
- https://github.com/victorb/open-registry
