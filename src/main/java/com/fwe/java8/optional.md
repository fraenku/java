#Optional

``` Java
Optional<Dish> dish =
menu.stream()
.filter(Dish::isVegetarian)
.findAny();
```

Methods:

- isPresent()
- ifPresent(Consumer<T> block)
- T get()
- T orElse(T other)

gives interesting possibilites:

``` Java
menu.stream()
.filter(Dish::isVegetarian)
.findAny()
.ifPresent(d -> System.out.println(d.getName());
```

### A bit of history
Null references were introduced in 1965 by Tony Hoare while designing ALGOL W, 
one of the first typed programming languages.
Simply because it was easy to implement, but later on he regretted his decisions
and called it "my billion-dolar mistake"

Fixing null-pointers:
``` Java
public String getCarInsuranceName(Person person) {
  return person.getCar().getInsurance().getName();
}


public String getCarInsuranceName(Person person) {
  if (person != null) {
    Car car = person.getCar();
    if (car != null) {
      Insurance insurance = car.getInsurance();
      if (insurance != null) {
        return insurance.getName();
      }
    }
  }
  return "Unknown";
}
``` 

This code is not optimal in terms of readability. 


####Solutions
Groovy introduced the save navigation operator ?.
``` Groovy
def carInsuranceName = person?.car?.insurance?.name
```

However this is not reflecting about if it senseful that there might
be null-values here, it feels more like a workaround.

Other programming languages like Haskell includes a Maybe type, which
encapsulates an optional value. Java 8 takes a similar approach with
Optional<T>

The Optional has many advantages over null:

- It makes clearly visible that a value might be missing
- 

### Creation of Optional
``` Java
  Optional<Car> optCar = Optional.empty();
  Optional<Car> optCar = Optional.of(car) //raises NullPointerException if car is null
  Optional<Car> optCar = Optional.ofNullable(car);

```

The null-checking can be avoided with Optional
``` Java
public String getCarInsuranceName(Optional<Person> person) {
  return person.flatMap(Person::getCar)
    .flatMap(Car::getInsurance)
    .map(Insurance::getName)
    .orElse("Unknown");
```
Well, honestly I still prefer the groovy-way...

### Summary
- null references have been historically introduced in programming languages
to generally signal the absence of a value.
- Java 8 introduces the class java.util.Optional<T> to model the presence or
absence of a value.
- You can create Optional objects with the static factory methods Optional.empty,
Optional.of, and Optional.ofNullable.
-  The Optional class supports many methods such as map, flatMap, and filter,
which are conceptually similar to the methods of a stream.
- Using Optional forces you to actively unwrap an optional to deal with the
absence of a value; as a result, you protect your code against unintended null
pointer exceptions.
- Using Optional can help you design better APIs in which, just by reading the
signature of a method, users can tell whether to expect an optional value.
Licensed to