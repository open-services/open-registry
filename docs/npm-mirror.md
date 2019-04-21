## Requirements for Open-Registry npm mirror

- Resillient to failures of upstream mirror
  - If there is a failure returned from the npm registry, try again
  - If we already have the package, no need to fetch it again
