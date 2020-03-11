# EBC4J

http://www.object-zoo.net/ebc4j  
[ebc4j@object-zoo.net](mailto:ebc4j@object-zoo.net)

EBC4J is a generic Java API that can be used to implement event based components.
It builds upon the actions and events of the Events4J API and provides base and
helper classes that can be used to create EBCs for loosely coupled systems, e.g.
the implementation of a flow design.

It is open source software released under the terms of the MIT License found in
the file "LICENSE.txt"

## Download
EBC4J can be downloaded form the [Releases Page](http://object-zoo.net/ebc4j/releases).

## Motivation
#### Problems solved:
* Provide EBC interfaces and base classes to hide boilerplate code from EBC implementors
* Include trace logging of EBC activity with no additional implementation overhead
* Provide ready to use generic Join EBCs to join data flows
* Provide easy to use mock objects for EBC testing

#### Design goals:
* Minimum number of interfaces that are still suitable for most situations
* Generic implementation adapts to most use cases
* Allow flexible use through strategy object composition for EBCs and Joins
* Usable in [GWT](http://code.google.com/intl/en-US/webtoolkit/) applications
* __Zero__ dependencies (except [Events4J API](http://object-zoo.net/events4j) of course)

#### Non-goals:
* Provide dependency injection framework (there are enough that can be used together with EBC4J)
* Provide automatic wiring/execution environment

## How To Use It
* See yourself in the [CSV Viewer example application](http://object-zoo.net/appkata-csv)
* Browse the [JavaDoc](http://doc.object-zoo.net/ebc4j/api)

## Development Tools

* [IntelliJ IDEA](https://www.jetbrains.com/idea/) (IDE)
* [Infinitest](http://infinitest.github.com/) (Continuous Testing)
* [Gradle](https://gradle.org/) (Automated build)

## Release History
* __1.3__  Renewed
    - Added Java 8 functions and lambdas support
    - Switched from Maven to Gradle
    - Switched from Jnario to JUnit
    - Switched from Eclipse to IntelliJ IDEA
    - Switched from Assembla to Github
* __1.2__ New Features
    - Fixed GWT support
    - Refactorings
    - Added signal Joins
    - Added Pair mappings
    - Added Flow building DSL
    - Added Flow executor
    - Added Split implemenetations
    - Added State mechanism
* __1.1__  New features
    - Added reset option to Joins 
* __1.0__  First final release
    - Completed [JavaDoc](http://doc.object-zoo.net/ebc4j/api)  
    - Refactored Joins to favor composition over inheritance
* __0.9__  First preview release
