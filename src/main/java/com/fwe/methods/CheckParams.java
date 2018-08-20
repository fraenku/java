package com.fwe.methods;

import java.util.Objects;

/**
 * Created by tkwtf on 07.08.2018.
 */
public class CheckParams {

    /**
     *
     *
     * @throws NullPointerException if i is Null
     */
    public void thinkOfRestrictions(Integer i) {
        Objects.requireNonNull(i, "Integer should not be null");
    }

    private void helper(long[]a , int offset, int length) {
        assert offset > 0 && offset <= a.length;
    }
}
