package com.fwe.objects_creation_destroy.singletons;

public class SingletonTraditionalWay2 {

    //traditional way

    private static final SingletonTraditionalWay2 INSTANCE = new SingletonTraditionalWay2();
    private SingletonTraditionalWay2() {
    }

    public static SingletonTraditionalWay2 getInstance() {
        return INSTANCE;
    }
}
