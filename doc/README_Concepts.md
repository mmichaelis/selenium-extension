# General Concepts

Here you will find general concepts/contracts the code has been developed with.

## Put JUnit in Extra Modules

While the Selenium Extensions have been developed with focus on JUnit the basic API is packaged without transitive
JUnit dependencies. This is to allow to use them also outside of testing context or with different test frameworks
like TestNG.
