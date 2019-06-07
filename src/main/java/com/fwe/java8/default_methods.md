####Types of compatibility

Interfaces in Java 8 can have implementation code through default methods
and static methods.

- Default methods start with a default keyword and contain a body like class
methods do.
- Adding an abstract method to a published interface is a source incompatibility.
- Default methods help library designers evolve APIs in a backward-compatible way.
- Default methods can be used for creating optional methods and multiple inheritance
of behavior.
- There are resolution rules to resolve conflicts when a class inherits from several
default methods with the same signature.
- A method declaration in the class or a superclass takes priority over any default
method declaration. Otherwise, the method with the same signature in the
most specific default-providing interface is selected.
- When two methods are equally specific, a class can explicitly override a method
and select which one to call.

Binary compatibility means existing binaries running without errors continue to link
(which involves verification, preparation, and resolution) without error after introducing a
change. For example, just adding a method to an interface is binary compatible because
if it’s not called, existing methods of the interface can still run without problems.

In its simplest form, source compatibility means an existing program will still compile
after introducing a change. For example, adding a method to an interface isn’t source
compatible; existing implementations won’t recompile because they need to implement
the new method.

Finally, behavioral compatibility means running a program after a change with the
same inputs results in the same behavior. For example, adding a method to an interface
is behavioral compatible because the method is never called in the program (or
it gets overridden by an implementation).

#### Differences between Abstract classes and Interfaces
So what’s the difference between an abstract class and an interface? They both can
contain abstract methods and methods with a body.
First, a class can extend only from one abstract class, but a class can implement multiple
interfaces.
Second, an abstract class can enforce a common state through instance variables
(fields). An interface can’t have instance variables.

#### Further usages of default methods
Some classes implementing an interface leave empty some method implementation because
they have no use for it. For example remove in the Iterator interface.
Since Java 8 Iterator provides a default implementation eliminating the need of an implementing
class to declare an empty remove method.

```Java
 default void remove() {
        throw new UnsupportedOperationException("remove");
    }
```    

In the case that a class inherits methods with the same signature, there are three rules to follow:

- A declaration in the class or superclass takes prority
- Otherweise the method with the same signature in the most specific default-providing interface is selected
- If there is still a conflict, you have to explicitly override the default methods


