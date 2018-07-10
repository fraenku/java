package com.fwe.objects_creation_destroy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkwtf on 14.06.2018.
 */
public class StaticFactoryMethod {

    public static StaticFactoryMethod getInstance() {
        List<String> advantages = new ArrayList<String>();
        advantages.add("Having names for clarity");
        advantages.add("Must not create a new object each, can returned cached instances, see Boolean.valueOf()");
        advantages.add("Can return subtypes");
        advantages.add("Class does not need to exist -> Service Provider Framework");
        System.out.println(advantages);
        return new StaticFactoryMethod();
    }
}
