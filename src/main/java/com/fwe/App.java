package com.fwe;

import com.fwe.objects_creation_destroy.NutritionBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        NutritionBuilder nutrionBuilder = new NutritionBuilder.Builder(240, 8).calories(33).fat(2).build();

    }
}
