> Draft API Description

# NPM Compatible API

## `GET /:package`
> Example Request: `https://npm.open-registry.dev/bs58`

Returns the metadata for a specific package

## `GET /:package/-/:tarball`
> Example Request: `https://npm.open-registry.dev/bs58/-/bs58-4.0.1.tgz`

Returns the tarball for a specific version

# Open-Registry Specific Endpoints

## `GET /`
> Example Request: `https://npm.open-registry.dev`

Returns a description of the current version of the registry

# GraphQL API
