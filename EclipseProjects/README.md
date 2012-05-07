EclipseProjects
===============
This is the accompanying code to the talk. Most of the code is in German.

Structure
---------
The project "Common" is a neccessary prerequisite for all other projects. It contains the shared classes as well as a single regression test. This test is used in every other project to prove that the overall implementation is still running.

Third party libraries
---------------------
The code is using [Joda Time](http://joda-time.sourceforge.net/index.html). The library files are inside the lib folder of the "Common" project.

We are using these classes to not have to hassle with java.util.Date.

What is it about?
-----------------
The code gives a simple example of an account. The entries are called "Umsatz". The code does not have a relation to an account. It assumes that all entries belong to the same account.

The code will cluster the entries by month and calculate the balance at the end of the month ("bestand") and the avarage balance in each month ("durchschnittsBestand").