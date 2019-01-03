package com.fwe.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Refers to Chapter 2 'Passing code with behavior parameteriation'
 *
 */
public class BehaviorParameterization {

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

    /* Duplicated code, what happens if you want to filter on the shape of the apple?*/
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

    //The Strategy-Pattern comes to help, externalizing the behavioral part of the method, however some boilerplate code is needed.
    //This could be improved by using anonymous classes
    public static List<Apple> filterAples(List<Apple> inventory, ApplePredicate p) {
            List<Apple> result = new ArrayList<>();
            for(Apple apple: inventory){
                if(p.test(apple)){
                    result.add(apple);
                }
            }
            return result;
    }

    public interface Predicate<T>{
        boolean test(T t);
    }

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

        Predicate<Apple> redAndHeavyAppleOrGreen =
                apples.and(a -> a.getWeight() > 150)
                        .or(a -> "green".equals(a.getColor()));

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
