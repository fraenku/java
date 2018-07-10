package com.fwe.objects_creation_destroy;

public class NutritionBuilder {

    //TODO: come back for the part with class hierarchies later on

    //telescoping constructor pattern -> add new constructors for each param
    // -> hard to read, easy to make mistakes for a client
    //java beans -> not immutable, could be in an inconsistent state

    //intellij has a "replace constructor with Builder-Refactoring"

    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;


    public static class Builder {
        //required
        private final int servingSize;
        private final int servings;

        //optional
        private int calories = 0;
        private int fat = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public NutritionBuilder build() {
            return new NutritionBuilder(this);
        }
    }

    private NutritionBuilder(Builder builder) {
        this.servingSize = builder.servingSize;
        servings = builder.servings;
        fat = builder.fat;
        calories = builder.calories;
        System.out.println("A good choice when designing constructors with more than a handful parameters, useful: 3");
    }
}

