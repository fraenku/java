package com.fwe.objects_creation_destroy.singletons;

public class SingletonTraditionalWay1 {

    //traditional way

    public static final SingletonTraditionalWay1 INSTANCE = new SingletonTraditionalWay1();
    private SingletonTraditionalWay1() {
    }

    //-> is not serialisation-safe -> all fields transient + provide readResolve-method
}

