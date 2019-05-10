# Streams

Streams area an update to the Java API that lets you manipulate collections of data in a declartive was, menaing that you express a query rather than code an ad hoc implementation for it (similar to an SQL-query).
In addition, streams can be processed in parallel transparently, without having to write any multithreaded code.

An example?

Here the code in Java7 to return the names of dished that are low in calories:
```java
List<Dish> lowCaloricDishes = new ArrayList<>();
for(Dish d: menu){
    if(d.getCalories() < 400){
        lowCaloricDishes.add(d);
    }
}
Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
    public int compare(Dish d1, Dish d2){
        return Integer.compare(d1.getCalories(), d2.getCalories());
    }
});
List<String> lowCaloricDishesName = new ArrayList<>();
for(Dish d: lowCaloricDishes){
    lowCaloricDishesName.add(d.getName());
}
```

In Java 8 it's much more concise
```java
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

List<String> lowCaloricDishesName =
    menu.stream()
    .filter(d -> d.getCalories() < 400)
    .sorted(comparing(Dish::getCalories))
    .map(Dish::getName)
    .collect(toList())
```    
    
To exploit a multicore architecture and execute this code in parallel, you need only
change stream() to parallelStream():    


> There have been many attempts at providing Java programmers with better libraries
  to manipulate collections. For example, Guava is a popular library created by Google.

Definition of streams: A sequence of elements from a source that supports data processing operations.
It involves three items:
- A data source (collection) to perform a query on
- A chain of intermediate operations to form a stream pipeline
- A terminal operation that executes the stream pipeline and produces a result

Collections are about data, streams are about computations.
Or a stream is a set of values spread out in time, a collection is a set of values
spread out in space. 

A stream is only traversable once. 


#### Flatmap
In a nutshell, the flatMap method lets you replace each value of a stream with
another stream and then concatenates all the generated streams into a single stream.
```  Java
List<Integer> numbers1 = Arrays.asList(1, 2, 3);
List<Integer> numbers2 = Arrays.asList(3, 4);
List<int[]> pairs =
    numbers1.stream()
        .flatMap(i -> numbers2.stream()
        .map(j -> new int[]{i, j})
    )
    .collect(toList());
```
The result is [(2, 4), (3, 3)].

findFirst and findAny -> differences?
The answer is parallelism.
Finding the first element is more constraining in parallel

####Reduce
Sum a collection of Integers:
```  Java
int sum = numbers.stream().reduce(0, (a + b) -> a + b); 
Optional<Integer> sum = numbers.stream().reduce((a , b) -> (a + b));
```
where 0 is the initial value. If no initial value is used, the return type
is Optional since numbers could be empty. 

Get the maximum or minimum. 
```  Java
Optional<Integer> sum = numbers.stream().reduce(Integer::max);
Optional<Integer> sum = numbers.stream().reduce((x,y) -> x<y?x:y);
```


transactions.stream().map(t -> t.getTrader()).filter(t -> "Cambridge".equals(t.getCity()).sorted()

####Numeric ranges
IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0)

####Building streams
```  Java
Stream.of("Java 8", "Java 9")
Arrays.stream(numbers)
Stream.iterate(0, n -> n + 2).limit(10).forEach(System::out) //potential infinite streams, use limit therefore
Stream.generate(Math::random).limit(4).forEach(System::out)
```
Stream.iterate can produces infinite streams, values are computed on demand. Big differences to collections.

####Collecting data with streams
```  Java
List<Transaction> transactions = transactionStream.collect(Collectors.toList());
```

the method collect expects a instance of the interface Collector
```  Java
IntSummaryStatistics menuStatistics =
menu.stream().collect(summarizingInt(Dish::getCalories));
```
returns a complete statistics containing count,s ume, min, average, max
```  Java
String shortMenu = menu.stream().map(Dish::getName).collect(joining());
```