package com.fwe.java8;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Streams {

    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <=n; i++) {
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
            .limit(n)
                .reduce(0L, Long::sum);
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }



    public static void main(String[] args) {
        System.out.println("Processors at disposal: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Iterative sum done in: " + measureSumPerf(Streams::iterativeSum, 10_000_000) + " msecs");
        System.out.println("Sequential sum done in: " + measureSumPerf(Streams::sequentialSum, 10_000_000) + " msecs");
        System.out.println("Parallel sum done in: " + measureSumPerf(Streams::parallelSum, 10_000_000) + " msecs");

        //note that iterative is fastest because it does not have to box/unbox. Parallel could be slower than sequential since
        //with 'iterate' the whole list of numbers isn't available at the beginning of the reduction process, making it impossible
        //to efficiently partition the stream in chunks. By flagging the stream as parallel, you’re just adding
        //to the sequential processing the overhead of allocating each sum operation on a
        //different thread.

        //have a try with rangeSum, allowing to work directly on long numbers without boxing/unboxing and having the whole list of numbers at disposal
        System.out.println("Parallel sum done in: " + measureSumPerf(Streams::rangedSum, 10_000_000) + " msecs");
        //interesting, using the right data structures if often more important than parallelizing
        System.out.println("Parallel sum done in: " + measureSumPerf(Streams::parallelRangedSum, 10_000_000) + " msecs");

        System.out.println("Parallel sum done in: " + measureSumPerf(Streams::sideEffectSum, 10_000_000) + " msecs");
        System.out.println("Parallel sum done in: " + measureSumPerf(Streams::sideEffectParallelSum, 10_000_000) + " msecs");
    }
}
