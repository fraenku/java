package com.fwe.java8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by tkwtf on 03.01.2019.
 *
 * (Apple a1)       -> a.getWeight() > 30
 * Lambda parameter    Lambda body (returns a boolean)
 *
 * (parameters) -> expression or (parameters) -> {statements; }
 *
 * Lambda can be used in the context of a function interface (a interface that specifies exactly one method).
 * It is a good practice to use @FunctionalInterface to declare it
 */
public class LamdaExpressions {

    //First identify try-around pattern (like opening and closing resources)
    public static String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }

    //Write a function interface
    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

    public static String processFileRefactored(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\develop\\java\\src\\main\\java\\com\\fwe\\java8\\data.txt"))) {
            return p.process(br);
        }
    }

    //there are some functional interface written for you :-) Predicate, Consumer, Function
    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T s: list) {
            result.add(f.apply(s));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
//        System.out.println()
        processFileRefactored((BufferedReader br) -> {String a = br.readLine(); System.out.println(a); return a;});

        //pretty concise huh?
        List<Integer> l = map(Arrays.asList("lambdas", "in", "action"), (String s) -> s.length());
        System.out.println(l);
    }



}
