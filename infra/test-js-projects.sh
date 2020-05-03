#! /usr/bin/env bash

set -ex

TMPDIR=$(mktemp -d)

cd $TMPDIR

function test() {
  echo "Hey $1"
  DIR=$(basename $1)
  git clone --depth=1 "$1" $DIR
  cd $DIR
  # rm -rf node_modules yarn.lock package-lock.json
  sed -i -e 's|https://registry.npmjs.org|https://npm.open-registry.dev|g' package-lock.json || true
  sed -i -e 's|http://registry.npmjs.org|https://npm.open-registry.dev|g' package-lock.json || true
  sed -i -e 's|https://registry.yarnpkg.com|https://npm.open-registry.dev|g' yarn.lock || true
  yarn --ignore-scripts --ignore-engines --ignore-platform --non-interactive --registry=https://npm.open-registry.dev/ --cache-folder=yarn-cache/
  # yarn --verbose --registry=https://npm.open-registry.dev/ --cache-folder=yarn-cache/
  # yarn --verbose --registry=http://npm.open-registry.test:2015/ --cache-folder=yarn-cache/
  cd ../
  rm -rf $DIR
}

test https://github.com/freeCodeCamp/freeCodeCamp.git
test https://github.com/vuejs/vue.git
test https://github.com/twbs/bootstrap.git
test https://github.com/facebook/react.git
test https://github.com/getify/You-Dont-Know-JS.git
test https://github.com/airbnb/javascript.git
test https://github.com/electron/electron.git
test https://github.com/nodejs/node.git
test https://github.com/axios/axios.git
test https://github.com/mrdoob/three.js.git
test https://github.com/justjavac/free-programming-books-zh_CN.git
test https://github.com/webpack/webpack.git
# not working sometimes due to them using their own mirror
# test https://github.com/atom/atom.git
test https://github.com/microsoft/TypeScript.git
test https://github.com/trekhleb/javascript-algorithms.git
test https://github.com/angular/angular.git
test https://github.com/mui-org/material-ui.git
test https://github.com/30-seconds/30-seconds-of-code.git
test https://github.com/expressjs/express.git
test https://github.com/chartjs/Chart.js.git
test https://github.com/h5bp/html5-boilerplate.git
test https://github.com/meteor/meteor.git
test https://github.com/lodash/lodash.git
test https://github.com/ionic-team/ionic.git
test https://github.com/storybooks/storybook.git
test https://github.com/ElemeFE/element.git
test https://github.com/Dogfalo/materialize.git
test https://github.com/yarnpkg/yarn.git
test https://github.com/nwjs/nw.js.git
test https://github.com/thedaviddias/Front-End-Checklist.git

rm -rf $TMPDIR

echo "ALL DONE! Great work"
exit 0
