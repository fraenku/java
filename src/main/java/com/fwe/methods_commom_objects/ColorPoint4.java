package com.fwe.methods_commom_objects;

import java.awt.*;

//favor composition over inheritance
public class ColorPoint4 {

    private final Color color;
    private final Point point;

    public ColorPoint4(Color color, Point point) {
        this.color = color;
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof ColorPoint4)) {
            return false;
        }
        ColorPoint4 cp = (ColorPoint4) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }

}
