package com.despectra.githubrepoview;

import java.util.Set;
import java.util.TreeSet;

/**
 * Algebraic sets operations implementation
 */
public class SetOperations {

    /** Intersects two sets
     * @param a Set A
     * @param b Set B
     * @param <T> set type parameter
     * @return A and B
     */
    public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        Set<T> tmp = new TreeSet<T>();
        for (T x : a)
            if (b.contains(x))
                tmp.add(x);
        return tmp;
    }

    /**
     *
     * @param a Set A
     * @param b Set B
     * @param <T> set parameter type
     * @return  A \ B
     */
    public static <T> Set<T> difference(Set<T> a, Set<T> b) {
        Set<T> tmp = new TreeSet<T>(a);
        tmp.removeAll(b);
        return tmp;
    }

}
