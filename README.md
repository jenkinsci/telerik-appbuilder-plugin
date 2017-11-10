> ## Progress will discontinue Telerik Platform on May 10th, 2018. -> [Learn more](https://www.telerik.com/platform-next-level)

# Progress Telerik AppBuilder Jenkins Plugin

## Overview
This plugin provides a simple way for Progress Telerik AppBuilder developers to execute cloud builds in a CI environment

### Dependencies
* [Apache Maven][maven] 3.0.4 or later

### Run in Docker
-------------------------
```shell
  $ docker run --name ab-jenkins -p 8080:8080 -p 50000:50000 -v /{ABSOLUTE_PATH}/jenkins_home:/var/jenkins_home -v /{ABSOLUTE_PATH}/telerik-appbuilder-plugin:/var/telerik-appbuilder-plugin telerikappbuilder/jenkins-appbuilder-plugin
```

### Run Plugin Locally
-------------------------
1. Build (with shell command '$mvn clean install') the project to produce `target/appbuilder-ci.hpi`
2. Remove any installation of the appbuilder-ci in `$user.home/.jenkins/plugins/`
3. Copy `target/appbuilder-ci.hpi` to `$user.home/.jenkins/plugins/`
4. Start/Restart Jenkins

[maven]: https://maven.apache.org/

### Publish to jenkins-ci.org
-------------------------
```shell
  $ docker exec -it {CONTAINER_ID} cd /var/telerik-appbuilder-plugin && mvn release:prepare release:perform -Dusername=... -Dpassword=...
```
