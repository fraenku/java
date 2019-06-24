# Java 8

The new features in Java 8 (Release 2014) are the biggest change to Java in the 18 years since
Java 1.0 was released in 1996. Nothing has been taken away, so all your existing Java code continues
to work—but the new features provide powerful new idioms and design patterns to help you write clearer, more concise code. 

## Success of Java
The write-once run-anywhere model of Java and the ability of early browsers
to (safely) execute Java code applets gave it a niche in universities, whose graduates
then populated industry. There was initial resistance to the additional run cost of Java
over C/C++, but machines got faster and programmer time became more and more
important.

Languages need to evolve to track changing hardware
or programmer expectations (if you need convincing, then consider that COBOL
was once one of the most important languages commercially).
To endure, Java has to evolve by adding new features. This evolution will be pointless unless the new features
are used, so in using Java 8 you’re protecting your way of life as a Java programmer.

The changes in Java 8 were driven by two 'climate'-changes:
- The arrival of multicore processors and therefore the need to exploit them. Making the code run faster requires parallel code.
- The arrival of Big Data with the need to manipulate collections of data with a declartive style.

The changes of Java 8 can be summarized to the following:

### Behavior parameterization (lambdas and method references)
To adapt the way a method is working (like sort) there must exist a way to handle a piece of code to the method (instead of list of flags).
Prior to Java 8 this was mostly done with Anonymous Classes. Java 8 provides now two new ways, borrowed from functional programming, to pass a piece
of code:
- Passing a lamba (apple -> apple.getWeight() > 150)
- Passing a method reference (Apple::isHeavy)

### Streams
Streams
The collection classes in Java, along with iterators and the for-each construct, have
served us honorably for a long time. It would have been easy for the Java 8 designers
to add methods like filter and map to collections, exploiting the lambdas mentioned
previously to express database-like queries. But they didn’t—instead they added a
whole new Streams API, the subject of chapters 4–7—and it’s worth pausing to consider
why.
What’s wrong with collections that requires them to be replaced or augmented
with a similar but different notion of streams? We’ll summarize it like this: if you have
a large collection and apply three operations to it, perhaps mapping the objects in the
collection to sum two of their fields, then filtering the sums satisfying some criterion,
and then sorting the result, you’ll make three separate traversals of the collection.
The Streams API instead lazily forms these operations into a pipeline, and then does a
single stream traversal performing all the operations together. This is much more efficient
for large datasets, and for reasons such as memory caches, the larger the dataset
the more important it is to minimize the number of traversals.
The other, no less important, reasons concern the ability to process elements in
parallel, which is vital to efficiently exploit multicore CPUs. Streams, in particular the
method parallel, allow a stream to be marked as suitable for parallel processing.
Recall here that parallelism and mutable state fit badly together, so core functional
concepts (side-effect-free operations and methods parameterized with lambdas and
method references that permit internal iteration instead of external iteration, as discussed
in chapter 4) are central to exploiting streams in parallel using map, filter,
and the like

###CompletableFututre
Java has provided the Future interface since Java 5. Futures are useful for exploiting
multicore because they allow a task to be spawned onto another thread or core and
allow the spawning task to continue executing along with the spawned task. When the
spawning task needs the result, it can use the get method to wait for the Future to
complete (produce its value).
Chapter 11 explains the Java 8 CompletableFuture implementation of Future.
Again this exploits lambdas. A useful, if slightly imprecise, motto is that “Completable-
Future is to Future as Stream is to Collection.” Let’s compare:

- Stream lets you pipeline operations and provides behavior parameterization
with map, filter, and the like, thus avoiding the boilerplate code you typically
have to write using iterators.

- Similarly, CompletableFuture provides operations such as thenCompose, then-
Combine, and allOf, which give functional-programming-style concise encodings
of common design patterns involving Futures, and let you avoid similar
imperative-style boilerplate code.

###Optional
The Java 8 library provides the class Optional<T>, which allows your code to specify
that a value is either a proper value of type T or is a missing value returned by the static
method Optional.empty. This is great for program comprehension and documentation;
it provides a data type with an explicit missing value—instead of the previous
error-prone use of the null pointer to indicate missing values, which we could never
be sure was a planned missing value or an accidental null resulting from an earlier
erroneous computation.
As chapter 10 discusses, if Optional<T> is used consistently, then programs should
never produce NullPointerExceptions. Again you could see this as a one-off, unrelated
to the rest of Java 8, and ask, “How does changing from one form of missing
value to another help me write programs?” Closer inspection shows that the class
Optional<T> provides map, filter, and ifPresent. These have similar behavior to
corresponding methods in the Streams class and can be used to chain computations,
again in functional style, with the tests for missing value done by the library instead of
user code. This internal testing versus external testing choice is directly analogous to
how the Streams library does internal iteration versus external iteration in user code.

###Default methods
There are other additions to Java 8, none of which particularly affect the expressiveness
of any individual program. But one thing that is helpful for library designers is the addition
to allow default methods to be added to an interface. Prior to Java 8, interfaces
defined method signatures; now they can also provide default implementations for
methods that the interface designer suspects not all clients will want to provide explicitly.
This is a great new tool for library designers, because it provides them with the
ability to augment an interface with a new operation, without having to require all clients
(classes implementing this interface) to add code to define this method. Therefore,
default methods are also relevant to users of libraries because they shield them
from future interface changes.

