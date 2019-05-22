package com.fwe.java7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;

    public static final long THRESHOLD = 10_000;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    /**
     * Some remarks:
     *
     * Calling the fork method on a subtask is the way to schedule it on the Fork-
     * JoinPool. It might seem natural to invoke it on both the left and right subtasks,
     * but this is less efficient than just directly calling compute on one of them. Doing
     * this allows you to reuse the same thread for one of the two subtasks and avoid
     * the overhead caused by the unnecessary allocation of a further task on the pool.
     *
     * Debugging a parallel computation using the fork/join framework can be tricky.
     * In particular, it’s ordinarily quite common to browse a stack trace in your favorite
     * IDE to discover the cause of a problem, but this can’t work with a fork-join
     * computation because the call to compute occurs in a different thread than the
     * conceptual caller, which is the code that called fork.
     *
     * As with parallel streams, you should never take for granted
     * that a computation using the fork/join framework on a multicore processor is
     * faster than the sequential counterpart.
     *
     */
    protected Long compute() {
        System.out.println("Forking " + Thread.currentThread().getName() + " calculates from " + start + " to " + end);
        int length = end - start;
        if(length <= THRESHOLD) {
            return computeSequentially();
        }
        //create a subtask to sum the first half of the array
        ForkJoinSumCalculator leftTask =
                new ForkJoinSumCalculator(numbers, start, start + length/2);
        //asynchronously execute the newly created subtask using another thread of the ForkJoinPool
        leftTask.fork();
        //create a subtask to sum the second half of the array
        ForkJoinSumCalculator rightTask =
                new ForkJoinSumCalculator(numbers, start + length/2, end);
        //execute this second subtask synchronously, potentially allowing further recursive splits
        Long rightResult = rightTask.compute();
        //read the result of the first subtask or wait it if it isn't ready
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        System.out.println("Joining " + Thread.currentThread().getName() + " calculates from " + start + " to " + end);
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(1, 30_000).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        System.out.println("Result: " + new ForkJoinPool().invoke(task));
    }
}
