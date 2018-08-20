package com.fwe.methods_commom_objects;

import java.awt.*;

/**
 * Created by tkwtf on 19.07.2018.
 */
public class ColorPoint3 extends Point {

    private final Color color;

    public ColorPoint3(int x, int y, Color c) {
        super(x,y);
        this.color = c;
    }

    //broken Liskov substitution principle
    @Override
    public boolean equals(Object o) {
        if (o == null|| o.getClass() != getClass()) {
            return false;
        }
        Point p = (Point) o;
        return p.equals(this);
    }

    @Override
    /**
     * Overide clone with a public method whose return type is the the class itself (covariant return types, meaning that an overriding method's return typec
     * can be a subclass of the overriden method's return type). This method should first calls super.clone then fix anx fields that need fixing.
     * Typically, this means copying any mutable objects that comprise the internal "deep structure" of the object and replacing the clone's references
     * to these objects wiht references to their copies.
     * Is this complexity necessary? Rarelly, if you extend a class that have already implemented Cloneable, you have no choice. Else use a copy constructor
     * or copy factory.
     * Exceptions are arrays, which are best copied with the clone method
     *
     */
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
