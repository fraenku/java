# Streams

Streams area an update to the Java API that lets you manipulate collections of data in a declarative was, meaning that you express a query rather than code an ad hoc implementation for it (similar to an SQL-query).
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
Note that streams can only be traversed once.


##Building streams
```  Java
Stream.of("Java 8", "Java 9")
Arrays.stream(numbers)
Stream.iterate(0, n -> n + 2).limit(10).forEach(System::out) //potential infinite streams, use limit therefore
Stream.generate(Math::random).limit(4).forEach(System::out)
####Numeric ranges
IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0)
```
Stream.iterate can produces infinite streams, values are computed on demand. Big differences to collections.

## Intermediate operations
Always returns a stream, are lazily executed

###Map, Filter, distinct, sorted
Get the unique surnames in uppercase of the first 15 book
authors that are 50 years old or over.
```  Java
library.stream()
 .map(book -> book.getAuthor())
 .filter(author -> author.getAge() >= 50)
 .distinct()
 .limit(15)
 .map(Author::getSurname)
 .map(String::toUpperCase)
 .sorted()
 .collect(toList());
```

### Flatmap
In a nutshell, the flatMap method lets you replace each value of a stream with
another stream and then concatenates all the generated streams into a single stream.
```  Java
List<String> phrases = Arrays.asList(
        "sporadic perjury",
        "confounded skimming",
        "incumbent jailer",
        "confounded jailer");

List<String> uniqueWords = phrases
        .stream() //Stream<List<String>>
        .flatMap(phrase -> Stream.of(phrase.split(" +"))) //Stream<String> 
        .distinct()
        .sorted()
        .collect(Collectors.toList());
System.out.println("Unique words: " + uniqueWords);
```

## Terminal operations

###Collect
A stream is terminated by a terminal operation, consuming a stream to produce a final result.
The class Collectors offers many static factory methods returning Collector-instances. 

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

Collect offers also grouping possibilities
```  Java
Map<Dish.Type, List<Dish>> dishesByType =
menu.stream().collect(groupingBy(Dish::getType));
```
Also multilevel grouping is possible
```  Java
Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
    menu.stream().collect(
    groupingBy(Dish::getType,
        groupingBy(dish -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        } )
    )
);
{MEAT={DIET=[chicken], NORMAL=[beef], FAT=[pork]},
FISH={DIET=[prawns], NORMAL=[salmon]},
OTHER={DIET=[rice, seasonal fruit], NORMAL=[french fries, pizza]}}
```

A special case of grouping is partitioning, grouping into two categories:
```  Java
Map<Boolean, List<Dish>> partitionedMenu =
menu.stream().collect(partitioningBy(Dish::isVegetarian));
```
Note that the same could be achieved with a filter, but you loose access to the filtered
elements.
```  Java
List<Dish> vegetarianDishes =
menu.stream().filter(Dish::isVegetarian).collect(toList());
```

###Reduce

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

###forEach
For performing side effects on elements

### About parallel data processing
It’s possible to turn a collection into a
parallel stream by invoking the method parallelStream on the collection source. A
parallel stream is a stream that splits its elements into multiple chunks, processing each
chunk with a different thread. Thus, you can automatically partition the workload of a
given operation on all the cores of your multicore processor and keep all of them
equally busy.

Don't expect parallel() to be a magic bullet. First:
- Applied on the wrong place it can produce a false result
- Parallelism can slow down the calculation if it applied on an unappropiate
pipeline, see the examples under Streams.java
- Keep in mind that parallelization doesn’t come for free. The parallelization
  process itself requires you to recursively partition the stream, assign the reduction
  operation of each substream to a different thread, and then combine the results
  of these operations in a single value. But moving data between multiple cores is also
  more expensive than you might expect, so it’s important that work to be done in parallel
  on another core takes longer than the time required to transfer the data from


In doubt if parallelisms brings a gain in performance, measure!

### For Advanced developers
The Collector interface can be implemented by yourself, but that's not an easy task.
```  Java
public interface Collector<T, A, R> {
    Supplier<A> supplier();
    BiConsumer<A, T> accumulator();
    Function<A, R> finisher();
    BinaryOperator<A> combiner();
    Set<Characteristics> characteristics();
}
```
- T is the generic type of the items in the stream to be collected.
- A is the type of the accumulator, the object on which the partial result will be
accumulated during the collection process.
- R is the type of the object (typically, but not always, the collection) resulting
from the collect operation.

The supplier is the starting point, an empty accumulator used during the collection process.
Example:
```  Java
public Supplier<List<T>> supplier() {
return () -> new ArrayList<T>();
}
```
The accumulator explains what is done when traversing the nth element in the stream after having
collected the first n-1 items.
```  Java
public BiConsumer<List<T>, T> accumulator() {
  return (list, item) -> list.add(item);
```

The finisher is invoked at the end of the accumulation process, after having completely traversed
the stream, in order t transform the accumulator object into the final result.
Example:
```  Java
public Function<List<T>, List<T>> finisher() {
return Function.identity();
}
``` 

The combiner method, defines how the accumulators resulting from the reduction of
different subparts of the stream are combined when the subparts are processed in parallel.

```  Java
public BinaryOperator<List<T>> combiner() {
  return (list1, list2) -> {
  list1.addAll(list2);
  return list1; }
}

```

Characteristics method
The last method, characteristics, returns an immutable set of Characteristics,
defining the behavior of the collector—in particular providing hints about whether
the stream can be reduced in parallel.
```  Java
Example:
public Set<Characteristics> characteristics() {
return Collections.unmodifiableSet(EnumSet.of(
IDENTITY_FINISH, CONCURRENT));
}
```