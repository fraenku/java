package com.fwe.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

/**
 * Refers to Chapter 2 'Passing code with behavior parameteriation'
 *
 */
public class BehaviorParameterization {

    /**
     * First attempt:
     * We would like to filter out all green apples
     */
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory){
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * Second attempt:
     * Well, first attempt does not cope well with changes if the farmer wants multiple colours, light green, dark red and so on.
     * Therefore we try to abstract the colour:
     */
    public static List<Apple> filterApplesByColor(List<Apple> inventory,
                                                  String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory){
            if ( apple.getColor().equals(color) ) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * Second attempt:
     * Too easy, right? Let's complicate the example a bit. The farmer comes back to you and says, "It would be really cool to differentiate
     * between light apples and heavy apples"
     */
    public static List<Apple> filterApplesByWeight(List<Apple> inventory,
                                                   int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory){
            if ( apple.getWeight() > weight ){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     *  Third attempt:
     *  Now we have duplicated most of the implementation for traversing the inventory and applying the filtering criteria on each apple.
     *  This is not optimal because it breaks DRY (don't repeat yourself). Therefore we go for a third attempt:
    */
    public static List<Apple> filterApples(List<Apple> inventory, String color,
                                           int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory){
            if ( (flag && apple.getColor().equals(color)) ||
                    (!flag && apple.getWeight() > weight) ){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     *  Forth attempt:
     *  The third attempt looks complicate and covers only a specific use-case. We need something more flexible.
     *  The Strategy-Pattern (define a family of algorithms, encapsulate each one and select one at run-time)
     *  comes to help, externalizing the behavioral part of the method. In this case ApplePredicate is the family of algorithms
     *  and AppleHeavyWeightPredicate a concrete strategy
     */
    public static List<Apple> filterApples4(List<Apple> inventory, ApplePredicate p) {
            List<Apple> result = new ArrayList<>();
            for(Apple apple: inventory){
                if(p.test(apple)){
                    result.add(apple);
                }
            }
            return result;
    }

    /**
     *  Fifth attempt:
     *  Our strategy-pattern is working well, but the the creation of additional classes is quite cumbersome, let's
     *  make use of anonymous classes
     */
    public static List<Apple> redApples = filterApples4(null, new ApplePredicate() {
        public boolean test(Apple apple){
            return "red".equals(apple.getColor());
        }
    });

    /**
     * Sixth attempt:
     * With Java 8 we have now tools at disposal to further remove boilerplate code
     */
    public static List<Apple> greenApples = filterApples4(null, (Apple apple) -> "green".equals(apple.getColor()));

//    public interface Predicate<T>{
//        boolean test(T t);
//    }

    /**
     * Seventh attempt:
     * Maximum flexibility, we can now nt only filter apples but also bananas or Integers!
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T e: list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result;
    }

    public static void main(String[] args) {

        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple("pink",  20));
        apples.add(new Apple("red", 100));
        apples.add(new Apple("green", 100));

        List<Apple> redApples = filter(apples, (Apple apple) -> "red".equals(apple.getColor()));

        //be aware that lambda can not modify local variables (their on the stack, not the heap)
         apples.sort((Apple a, Apple b) -> a.getWeight().compareTo(b.getWeight()));
         //the same can be done using a method-reference (basically again syntactic sugar)
         apples.sort(comparing(Apple::getWeight));

        List<String> str = Arrays.asList("a","b","A","B");
        str.sort(String::compareToIgnoreCase);

//        Predicate<Apple> redAndHeavyAppleOrGreen =
//                apples.and(a -> a.getWeight() > 150)
//                        .or(a -> "green".equals(a.getColor()));

    }



    public static class Apple {

        public Apple(String color, Integer weight) {
            this.color = color;
            this.weight = weight;
        }

        private String color;
        private Integer weight;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }
    }

    public interface ApplePredicate {

        public boolean test(Apple apple);

    }

    public class ApplePredictStrategy implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 50;
        }
    }

}
