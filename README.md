# DOM / SAC / CSSOM / SVG Benchmarks

You can build and run the benchmarks in this repository with either Maven or Gradle, Java 11 or later is required:

## Gradle
First, produce the benchmarks jar file, then run the required benchmark(s):

1) `./gradlew build`
2) `java -jar build/libs/benchmarks.jar <benchmark-name-regexp>`

You do not need to have Gradle installed on your system (it uses the `gradlew` wrapper script).

But if you just want to check that a benchmark (matched by a regular expression)
works, run the following:

```shell
./gradlew runJmh --args <regexp>
```

## Maven
As a pre-requisite, you (obviously) must have Apache Maven installed.

Then run:

1) `mvn package` (or '`mvn install`')
2) `java -jar buildMaven/benchmarks.jar <benchmark-name-regexp>`

## Results
You can view some results at https://css4j.github.io/benchmarks.html
