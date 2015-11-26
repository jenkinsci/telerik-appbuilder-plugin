# AppBuilder Jenkins Plugin

## Overview
This plugin provides a simple way for AppBuilder developers to execute cloud builds in a CI environment

### Dependencies
* [Apache Maven][maven] 3.0.4 or later

### Targets
```shell
  $ mvn clean install
```

Installing Plugin Locally
-------------------------
1. Build the project to produce `target/appbuilder-ci.hpi`
2. Remove any installation of the appbuilder-ci in `$user.home/.jenkins/plugins/`
3. Copy `target/appbuilder-ci.hpi` to `$user.home/.jenkins/plugins/`
4. Start/Restart Jenkins

[maven]: https://maven.apache.org/
