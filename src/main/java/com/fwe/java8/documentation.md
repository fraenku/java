# Java 8, what's the fuss about?

Put simply, the new features in Java 8 (Release 2014) are the biggest change to Java in the 18 years since
Java 1.0 was released in 1996. Nothing has been taken away, so all your existing Java code continues
to work—but the new features provide powerful new idioms and design patterns to
help you write clearer, more concise code. At first you might think (as with all new features),
“Why are they changing my language again?” But then, after a bit of practice,
comes the revelation that you’ve just used the features to write shorter, clearer code in
half the time you expected—and you realize you could never go back to “old Java” again.


####Why was Java so successful
The write-once run-anywhere model of Java and the ability of early browsers
to (safely) execute Java code applets gave it a niche in universities, whose graduates
then populated industry. There was initial resistance to the additional run cost of Java
over C/C++, but machines got faster and programmer time became more and more
important.

Languages need to evolve to track changing hardware
or programmer expectations (if you need convincing, then consider that COBOL
was once one of the most important languages commercially).
To endure, Java has to
evolve by adding new features. This evolution will be pointless unless the new features
are used, so in using Java 8 you’re protecting your way of life as a Java programmer.

###Streams
Ideas coming from linux

`cat file1 file2 | tr "[A-Z]" "[a-z]" | sort | tail -3`

Collections are about storing and accessing data, whereas Streams are about describing computations on data. 

Using methods and lambdas as first-class values, and the idea
that calls to functions or methods can be efficiently and safely executed in parallel in
the absence of mutable shared state. Both of these ideas are exploited by the new
Streams API.

###Passing code to methods with behavior parametrization
The whole point of a programming language is to manipulate values. Values are called first-class citizens and consist
of primitive values (double, int) or objects (Integer, String). Other structures in our programming languages, which perhaps help us express the structure of values but which can’t
be passed around during program execution, are second-class citizens. Values as listed previously are first-class Java citizens, but various other Java concepts, such as methods
and classes, exemplify second-class citizens. Methods are fine when used to define classes, which in turn may be instantiated to produce values, but neither are values
themselves. So does this matter? Yes, it turns out that being able to pass methods
around at run-time, and hence making them first-class citizens, is very useful in programming,
and so the Java 8 designers added this ability to Java. Instead of
`File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
 public boolean accept(File file) {
 return file.isHidden();
 }
 });`
we can use either a method reference:
`File[] hiddenFiles = new File(".").listFiles(File::isHidden);`
or a Lambda-expression (x) -> x + 1

Real live examples of behavior parametrization are comparing a list with a comparator, running runnable in a thread or add a gui event handler.
See BehaviorParameterization for an iterative approach of improving a method.

###Default methods
Adding a
new method to an interface means all concrete classes must provide an implementation
for it. Language designers have no control on all existing implementations of
Collections, so you have a bit of a dilemma: how can you evolve published interfaces
without disrupting existing implementations? The Java 8 solution is to break the last link—an interface can now contain method
signatures for which an implementing class doesn’t provide an implementation! So
who implements them? The missing method bodies are given as part of the interface
(hence default implementations) rather than in the implementing class.
This provides a way for an interface designer to enlarge an interface beyond those
methods that were originally planned—without breaking existing code. Java 8 uses the
new default keyword in the interface specification to achieve this. For example, in Java 8 you can now call the sort method directly on a List. This is
made possible with the following default method in the Java 8 List interface, which
calls the static method Collections.sort:
`default void sort(Comparator<? super E> c) {
Collections.sort(this, c);
}`