# EclipseProjects
This is the accompanying code to the talk.

## Structure
The project "Common" is a necessary prerequisite for all other projects. It contains the shared classes as well as a single regression test. This test is used in every other project to prove that the overall implementation is still correct and running.

## What is it about?
The code gives a simple example of an account. The entries are called "Umsatz". The code does not have a relation to an account. It assumes that all entries belong to the same account.

The code will cluster the entries by month and calculate the balance at the end of the month ("bestand") and the average balance in each month ("durchschnittsBestand").

### How to use the code
The projects show the transformation steps from a typical procedural "push"-style implementation to an object-oriented "pull"-style.

We start with project "Push". Next we have the numbered projects:

1. we start preparing the "extract method" refactoring by introducing a parameter object
1. we extract the body of the for-loop
1. we move the method to the parameter object
1. we slightly change the extracted method by instantiating a new parameter object for each call
1. we move the method parameters to the constructor
1. we separate the calculations of each value
1. we move the logic to the getters, thereby getting rid of their fields
1. we change the logic of the complicated calculation and add a test for it
1. ...

The final code can be seen in project "Pull". Here we added a few more extras. Look around and don't be shy to ask us or comment.

## Third party libraries

All third party library files are inside the lib folder of the "Common" project.

### Joda Time

We are using [Joda Time](http://joda-time.sourceforge.net/index.html) to avoid the hassle with java.util.Date.

### JUnit

The unit tests are driven by [JUnit](http://junit.org).
