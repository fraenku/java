package com.fwe.methods_commom_objects;

import java.awt.*;

public class ColorPoint2 extends Point {

    private final Color color;

    public ColorPoint2(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    //fulfills symmetry but not transitivity
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        if(!(o instanceof ColorPoint2)) {
            return o.equals(this);
        }
        return super.equals(o) && ((ColorPoint2) o).color == color;
    }



}
