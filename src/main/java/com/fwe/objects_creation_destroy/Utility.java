package com.fwe.objects_creation_destroy;


//a utility-class (like java.util.Collections which should not been instantiated
public class Utility {

    //suppress default constructor by adding a private constructor
    //having side-effect that class cannot be subclassed, a subclass
    //will always hav eto call superclass constructor
    private Utility() {
        throw new AssertionError();
    }
}
