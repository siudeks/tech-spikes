# Actors creation in terms of memory consumptions

* creates max number of actors as long as can't create more
* displays number of created actors and memory consumptions
* stops work when out of memory

## Optional: Before starting the project check, if tech stack is used with newest versions

* mvn versions:display-plugin-updates
* mvn versions:display-dependency-updates

## How to run - quick version to see how it works in general

* mvn clean compile
* mvn exec:java -D"exec.mainClass=net.siudek.Program"

## How to run - long version with memory limitation

* mvn clean verify
* java -jar -Xmx5m target/crash-test-akka-1.0-SNAPSHOT-allinone.jar net.siudek.AppActor
  ... and observer increasing number of created actors
  in my case displayed result is about 750 created actors before observing **java.lang.OutOfMemoryError**

## Interesting articles using to build the project

* [Upgrade project to java 11](https://winterbe.com/posts/2018/08/29/migrate-maven-projects-to-java-11-jigsaw/)
