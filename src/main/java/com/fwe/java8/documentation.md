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



###Lambdas
Lambdas are
- Anonymous, they do not have a name
- Function, they are not bound to a class like a method, but have also a list of parameters, a body, a return type and a possible list of exceptions
- Passed around
- Concise

The basic syntax is
`(parameters) -> expression`
or
`(parameters) -> { statements; }`

Lambdas can be used where a function interface is expected. A function interface is an interface with only one abstract method, like Comparator or Runnable
The lambda expression can be assigned to a variable or passed to a method if the lambda expression has
the same signature as the abstract method of the functional interface.
 
It's a good practice to declare function interface with the annotation
@FunctionalInterface

Caveat!
Lambdas have only access tu surrounding local variables if they are final,
since local variable are stored on the Stack and not the heap. 

**Method-References**

Can be used as shortcuts

(args) -> ClassName. staticMethod(args)
ClassName::staticMethod

(arg0, rest) -> arg0.instanceMethod(rest)
ClassName::instanceMethod

(args) -> expr. instanceMethod(args)
expr.instanceMethod

**Constructor-References**

`Function<Integer, Apple> c2 = Apple::new;
Apple a2 = c2.apply(110);`

is the same as

`Function<Integer, Apple> c2 = (weight) -> new Apple(weight);
Apple a2 = c2.apply(110);
`

The capability of referring to a constructor without instantiating it enables interesting
applications. For example, you can use a Map to associate constructors with a string
value. You can then create a method giveMeFruit that, given a String and an Integer,
can create different types of fruits with different weights:
`
static Map<String, Function<Integer, Fruit>> map = new HashMap<>();
static {
  map.put("apple", Apple::new);
  map.put("orange", Orange::new);
// etc...
}`

`public static Fruit giveMeFruit(String fruit, Integer weight){
return map.get(fruit.toLowerCase())
.apply(weight);
}`

**Lambda in a nutshell**

To wrap up this chapter and all we’ve discussed on lambdas, we continue with our initial
problem of sorting a list of Apples with different ordering strategies and show how
you can progressively evolve a naïve solution into a concise solution, using all the concepts
and features explained so far in the book: behavior parameterization, anonymous
classes, lambda expressions, and method references.

You’re lucky; the Java 8 API already provides you with a sort method available on
List so you don’t have to implement it. So the hard part is done! But how can you
pass an ordering strategy to the sort method? Well, the sort method has the following
signature:

`void sort(Comparator<? super E> c)`

***Step 1: pass code***

It expects a Comparator object as argument to compare two Apples! This is how you
can pass different strategies in Java: they have to be wrapped in an object. We say that
the behavior of sort is parameterized: its behavior will be different based on different
ordering strategies passed to it.
Your first solution looks like this:

`public class AppleComparator implements Comparator<Apple> {
    public int compare(Apple a1, Apple a2){
        return a1.getWeight().compareTo(a2.getWeight());
    }
}
inventory.sort(new AppleComparator());`

***Step 2: use an anonymous class***
Rather than implementing Comparator for the purpose of instantiating it once, you
saw that you could use an anonymous class to improve your solution.

`inventory.sort(new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2){
        return a1.getWeight().compareTo(a2.getWeight());
    }
});`

***Step 3: use lambda expression***
But your current solution is still verbose. Java 8 introduces lambda expressions, which
provide a lightweight syntax to achieve the same goal: passing code. You saw that a
lambda expression can be used where a functional interface is expected. As a reminder,
a functional interface is an interface defining only one abstract method. The signature
of the abstract method (called function descriptor) can describe the signature of a
lambda expression. In this case, the Comparator represents a function descriptor (T,
T) -> int. Because you’re using apples, it represents more specifically (Apple, Apple)
-> int. Your new improved solution looks therefore as follows:

`inventory.sort((Apple a1, Apple a2)
    -> a1.getWeight().compareTo(a2.getWeight())
);`

***Step 4: use method references***

We explained that the Java compiler could infer the types of the parameters of a lambda
expression by using the context in which the lambda appears. So you can rewrite your
solution like this:

`inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));`

Can you make your code even more readable? Comparator has a static helper method
called comparing that takes a Function extracting a Comparable key and produces a
Comparator object (we explain why interfaces can have static methods in chapter 9).
It can be used as follows (note that you now pass a lambda with only one argument:
the lambda specifies how to extract the key to compare with from an apple):

`Comparator<Apple> c = Comparator.comparing((Apple a) -> a.getWeight());`

**Composing**
Several functional interfaces contain convenient methods which allows chaining of lambdas:

`Predicate<Apple> redAndHeavyAppleOrGreen =
redApple.and(a -> a.getWeight() > 150)
.or(a -> "green".equals(a.getColor()));`

`Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.andThen(g);
int result = h.apply(1);
`

> Refactoring hint: Think of replacing annonymous classes by lambda expressions. However caveat: This referes to the anonymous
class, while this refers to the enclosing class in a lambda expression. Furhtermore anonymous classes are allowed to shadow variables 
from the enclosing class. Lambda expressions can't.  
> 

**Summary**

Following are the key concepts you should take away from this chapter:
- A lambda expression can be understood as a kind of anonymous function: it
doesn’t have a name, but it has a list of parameters, a body, a return type, and
also possibly a list of exceptions that can be thrown.
- Lambda expressions let you pass code concisely.
- A functional interface is an interface that declares exactly one abstract method.
- Lambda expressions can be used only where a functional interface is expected.
Lambda expressions let you provide the implementation of the abstract method
of a functional interface directly inline and treat the whole expression as an instance
of a functional interface.
- Java 8 comes with a list of common functional interfaces in the java.util
.function package, which includes Predicate<T>, Function<T, R>, Supplier<T>,
Consumer<T>, and BinaryOperator<T>, described in table 3.2.
- There are primitive specializations of common generic functional interfaces
such as Predicate<T> and Function<T, R> that can be used to avoid boxing
operations: IntPredicate, IntToLongFunction, and so on.
- The execute around pattern (that is, you need to execute a bit of behavior in
the middle of code that’s always required in a method, for example, resource
allocation and cleanup) can be used with lambdas to gain additional flexibility
and reusability.
- The type expected for a lambda expression is called the target type.
- Method references let you reuse an existing method implementation and pass
it around directly.
- Functional interfaces such as Comparator, Predicate, and Function have default methods
 that can be used to combine lambda expressions.