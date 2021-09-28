# DOM / SAC / CSSOM Benchmarks

You can build and run the benchmarks in this repository with either Maven or Gradle, Java 11 or later is required:

## Gradle
First, produce the benchmarks jar file, then run the required benchmark(s):

1) `./gradlew build`
2) `java -jar build/libs/benchmarks.jar <benchmark-name-regexp>`

You do not need to have Gradle installed on your system (it uses the `gradlew` wrapper script).

## Maven
As a pre-requisite, you must have installed:

1) Apache Maven.
2) The necessary [`css4j`](https://raw.githubusercontent.com/css4j/css4j-dist/master/maven/install-css4j.sh) [`dependencies`](https://raw.githubusercontent.com/css4j/css4j-dist/master/maven/install-jclf.sh) in you local Maven repository.

Then run:

1) `mvn package` (or '`mvn install`')
2) `java -jar build/benchmarks.jar <benchmark-name-regexp>`

## Results
You can view some results at https://css4j.github.io/benchmarks.html
