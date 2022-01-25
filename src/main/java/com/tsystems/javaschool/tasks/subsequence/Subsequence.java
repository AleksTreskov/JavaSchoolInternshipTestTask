package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here

        //Checking fot null or empty x or
        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }
        if (x.isEmpty()) {
            return true;
        }
        //if x.size >y.size y list can't become equals to x list
        if (x.size() > y.size()) {
            return false;
        }
        //Creating temp list and adding values that contains x, then return comparing lists
        List temp = new ArrayList();
        for (Object a : y
        ) {
            if (x.contains(a) && !temp.contains(a)) {
                temp.add(a);
            }
        }
        return temp.equals(x);
    }

}
