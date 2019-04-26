Open-registry is new and there are some known issues to be resolved.

## npm audit

Currently if you run `npm audit` you will receive an error.

```bash
$ npm audit
npm ERR! code ENOAUDIT
npm ERR! audit Your configured registry (https://npm.open-registry.dev/) does not support audit requests.

npm ERR! A complete log of this run can be found in:
npm ERR!     /home/user/.npm/_logs/2019-04-26T16_38_53_880Z-debug.log
```

Possible solutions include proxying the audit requests to npm, or implement our own. We are weighing the pros and cons and will update our roadmap accordingly. Meanwhile a workaround can be achieved by using npmrc to configure multiple profiles, with one profile pointing back to NPMs registry for audit purposes.

The NPM docs detail this process with respect to [configuration as an Enterprise user](https://docs.npmjs.com/configuring-your-registry-settings-as-an-npm-enterprise-user). Or you can simply follow our instructions here.

1 - Install npmrc if it's not already available

```bash
npm i npmrc -g
```

2 - Create a profile for the public NPM registry

```bash
npmrc -c npm
npm config set registry https://registry.npmjs.com/
```

3 - Create a second profile for the Open Registry

```bash
npmrc -c open
npm config set registry https://npm.open-registry.dev
```

4 - Switch registries using the `npmrc profile-name` syntax. E.g. to switch to NPM to run an audit use:

```bash
npmrc npm
npm audit
```

And switch back to Open Registry for everything else with:

```bash
npmrc open
```

Not sure which registry you have activated? Check by running `npmrc` by itself:

```bash
$ npmrc
Available npmrcs:

default
npm
* open
```
