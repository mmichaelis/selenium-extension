# Information for Developers

## Contracts

The API uses JSR 305 annotations especially to mark method return values and parameters as Nullable or Nonnull.

Mind that any Contract-Annotation-Libraries need to be added with scope _provided_ so that libraries using
Selenium Extensions are not forced to add them as transitive dependency.

## Q & A

### How to document Development Decisions?

If you are unsure how to implement a certain feature - just choose the one you like best and perhaps document your
decision with the keyword `DevNote` like in this example:

```java
// DevNote: Parameter could be Nullable but this conflicts with Static Code Analysis Approaches which are different
// in IntelliJ Idea and Findbugs.
void apply(String input);
```
