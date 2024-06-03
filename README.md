# XM

# Short description
This repository contains setup for test automation of swapi.dev rest api.
Following tools are use:
java 21, rest assured, json schema validator, cucumber, maven, spring framework, assertJ, lombok

# How to run tests
Test feature file is stored in \resources\features directory.
I can be run with following command:

```sh
mvn clean verify   
```

# Test reports
Test reports are generated after each test run and are stored in
\target\cucumber-report\cucumber.html