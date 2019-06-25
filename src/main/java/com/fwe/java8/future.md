#Completable Future
The Future interface was introduced in Java 5 to model a result made available at
some point in the future. It models an asynchronous computation and provides a reference
to its result that will be available when the computation itself is completed.
Triggering a potentially time-consuming action inside a Future allows the caller
Thread to continue doing useful work instead of just waiting for the operation’s result.
You can think of it as taking a bag of clothes to your favorite dry cleaner

``` Java
ExecutorService executor = Executors.newCachedThreadPool();
Future<Double> future = executor.submit(new Callable<Double>() {
    public Double call() {
        return someTimeConsumingComputation();
    }});
doSomethingElse();
try {
  Double result = future.get(1, TimeUnit.SECONDS);
} catch (ExecutionException ee) {
// the computation threw an exception
} catch (InterruptedException ie) {
// the current thread was interrupted while waiting
} catch (TimeoutException te) {
// the timeout expired before the Future completion
}
``` 

While the introduction of Features simplified things compared to former Thread-handling
options, it still lacked possibilities to express dependencies between results of a Feature.
That's why CompletableFutures were introduced.



##An example
It's good practice to write async API's, so let's go for it
``` Java
public Future<Double> getPriceAsync(String product) {
  CompletableFuture<Double> futurePrice = new CompletableFuture<>();
  new Thread( () -> {
    try {
      double price = calculatePrice(product);
      futurePrice.complete(price);
    } catch (Exception ex) {
      futurePrice.completeExceptionally(ex);
    }
  }).start();
  return futurePrice;
}
``` 

Can be simplified to
``` Java
public Future<Double> getPriceAsync(String product) {
  return CompletableFuture.supplyAsync(() -> calculatePrice(product));
}
``` 
 
Caller 
``` Java
Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
// Do some more tasks, like querying other shops
doSomethingElse();
// while the price of the product is being calculated
try {
  double price = futurePrice.get();
} catch (Exception e) {
  throw new RuntimeException(e);
}
```  


Formula, how many threads should be used:

> N<sub>threads</sub> = N<sub>CPU</sub> * U<sub>CPU</sub> * (1 + W/C)

N<sub>CPU</sub>: Number of cores, Runtime.getRuntime().availableProcessors()
U<sub>CPU</sub> is the target CPU utilization (between 0 and 1)
W/C is the ratio of wait time to compute time

If the wait time is very low and you want to use fully your cores,
then the formula will be ~ N<sub>threads</sub> = N<sub>CPU</sub>
but if you have a lot of waiting time  N<sub>threads</sub> > N<sub>CPU</sub>.
In this case it is worth to use your own executor

``` Java
Executors.newFixedThreadPool(Math.min(shops.size(), 100),
  new ThreadFactory() {
    public Thread newThread(Runnable r) {
    Thread t = new Thread(r);
    t.setDaemon(true);
    return t;
  }
});
```  

### Chaining asynch operations

Pipelining asynchronous operations with **thenCompose**:
``` Java
public List<String> findPrices(String product) {
    List<CompletableFuture<String>> priceFutures =
        shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(
        () -> shop.getPrice(product), executor))
        .map(future -> future.thenApply(Quote::parse))
        .map(future -> future.thenCompose(quote ->
        CompletableFuture.supplyAsync(
        () -> Discount.applyDiscount(quote), executor)))
    .collect(toList());
    
    return priceFutures.stream()
        .map(CompletableFuture::join)
    .collect(toList());
}
```  

Wait the completion of one asynch operation to use is input for a second asynch operation


Execute two asynch in parallel with **thenCombine**
``` Java
Future<Double> futurePriceInUSD =
CompletableFuture.supplyAsync(() -> shop.getPrice(product))
  .thenCombine(
    CompletableFuture.supplyAsync(
    () -> exchangeService.getRate(Money.EUR, Money.USD)),
    (price, rate) -> price * rate
));
```

The same in Java 7 with Futures 
``` Java
ExecutorService executor = Executors.newCachedThreadPool();
final Future<Double> futureRate = executor.submit(new Callable<Double>() {
    public Double call() {
        return exchangeService.getRate(Money.EUR, Money.USD);
    }});
Future<Double> futurePriceInUSD = executor.submit(new Callable<Double>() {
    public Double call() {
        double priceInEUR = shop.getPrice(product);
        return priceInEUR * futureRate.get();
    }});
``` 
The difference look small, but CompletableFuture offers more flexibility.
If you want to react immediately as soon as result gets available use **thenAccept** as Callback

``` Java
CompletableFuture[] futures = findPricesStream("myPhone")
.map(f -> f.thenAccept(System.out::println))
.toArray(size -> new CompletableFuture[size]);
CompletableFuture.allOf(futures).join();  //wait till all shops return
``` 