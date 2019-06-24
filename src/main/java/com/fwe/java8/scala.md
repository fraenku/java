#Scala

##Paradigms:
Object-oriented, functional, imperative

##History
Developed at the EPFL under Martin Odersky, work on it started in 2001, the first
version came out 2003.

##Features
- Everything is an object
- Syntactic sugar, like
  - Implicit constructors, getters and setters
- Traits (= interface) allowing multiple inheritance of behaviors and state



 ##Examples
  ``` Java
 public class Foo {
    public static void main(String[] args) {
        IntStream.rangeClosed(2, 6)
            .forEach(n -> System.out.println("Hello " + n +
            " bottles of beer"));
    }
 }
 ``` 
 
 ``` Scale
object Beer {
    def main(args: Array[String]){
        2 to 6 foreach { n => println(s"Hello ${n} bottles of beer") }
        }
    }
``` 
 