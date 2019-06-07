#Refactor with Java8

- Replace annonymous classes by lambda-expressions
- Use Method-reference whenevery possilbe, it makes the code more readable
``` Java
inventory.sort(
(Elephant a1, Elephant a2) -> a1.getWeight().compareTo(a2.getWeight()));

inventory.sort(comparing(Elephant::getWeight));
```
- Convert code processing a collection with an iterator with the Streams API
 ``` Java
List<String> books = new ArrayList<>();
for(Books book: books){
  if(book.getPages() > 300){
    books.add(book.getTitle());
  }
}

books.parellelStream()
 .filter(d -> d.getPages() > 300)
 .map(Book::getTitle)
 .collect(toList();
 ```
 
 ## Support for Design Patterns
 New language features often make existing code patterns or idioms less popular. For
 example, the introduction of the for-each loop in Java 5 has replaced many uses of
 explicit iterators because it’s less error prone and more concise
  
 ##### Strategy Pattern
 Clients depends on a strategy where a concrete implementation can be choosen
 at runtime.
 
 Lambda-expression can be used as strategies-implementations, therefore it is not necessary
 to write a concrete Java-class. (see [BehaviorParameterization](BehaviorParameterization.java))
 
 ##### Template method
 Is useful when you need to represent the outline of an algorithm and would keep the flexibility
 to change certain parts of it.
 
 ``` Java
 public void processCustomer(int id){
   Customer c = Database.getCustomerWithId(id);
   makeCustomerHappy(c);
 }
 abstract void makeCustomerHappy(Customer c);
   ```
   
 With Java 8 by adding an lambda-expression subclassing is not needed anymore
 ``` Java
 public void processCustomer(int id, Consumer<Consumer> makeCustomerHappy){
   Customer c = Database.getCustomerWithId(id);
   makeCustomerHappy.accept(c);
 }
   ``` 

 ##### Observer 
 Used when an object needs to automatically notify a list of other objects (observers) when some
 event happen. Often used in GUI applications to react on a mouse-click for example.
 
 Here again, Java 8 removes the need to instantiate classes implementing Observer.
 If the logic is complex however, you should
 still use classes. 
 ``` Java
 f.registerObserver((String tweet) -> {
   if(tweet != null && tweet.contains("money")){
     System.out.println("Breaking news in NY! " + tweet);
   }
 });
 
 f.notifiyObserver()
 
   ```  
  
##### Chain of responsibilities 
Used to create a chain of processing objects, doing some work and pass the result
to the successor. 
 
 ``` Java
 public abstract class ProcessingObject<T> {
   protected ProcessingObject<T> successor;
   
   public void setSuccessor(ProcessingObject<T> successor){
     this.successor = successor;
   }
   
   public T handle(T input){
     T r = handleWork(input);
     if(successor != null){
       return successor.handle(r);
     }
     return r;
   }
   
   abstract protected T handleWork(T input);
   
   
   ProcessingObject<String> p1 = new ToLowerCaseProcessing();
   ProcessingObject<String> p2 = new SpellCheckerProcessing();
   p1.setSuccessor(p2);
   String result = p1.handle("Let's have a tesst");
   ```  
 
 With Java 8 the same can be achieved by chaining Functions.
 
``` Java
 Function<String, String> pipeline = 
   p1.andThen(p2);
 String result = pipeline.apply("Let's have a tesst");
``` 


 
 

 
 
    
 
 
 