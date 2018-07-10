package com.fwe.objects_creation_destroy.singletons;

public enum SingletonBest {
    INSTANCE;

    //-> no issue with serialisation, safe for reflection-attacks
}
