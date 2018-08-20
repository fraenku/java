package com.fwe.methods;

import java.util.Date;

/**
 * Created by tkwtf on 20.08.2018.
 */
public class DefensiveCopies {

    private final Date start;
    private final Date end;

    public DefensiveCopies(Date start, Date end) {
        if(start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }

        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {
        Date start = new Date();
        Date end = new Date();
        DefensiveCopies dc = new DefensiveCopies(start, end);
        //Modified internals of dc and makes the invariant break!
        end.setYear(78);
        //change the constructor to this.start = new Date(start) or use Instant (immutable)
    }
}
