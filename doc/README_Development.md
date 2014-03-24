# Information for Developers

## Q & A

### How to document Development Decisions?

If you are unsure how to implement a certain feature - just choose the one you like best and perhaps document your
decision with the keyword `DevNote` like in this example:

```java
// DevNote: Parameter could be Nullable but this conflicts with Static Code Analysis Approaches which are different
// in IntelliJ Idea and Findbugs.
void apply(String input);
```
