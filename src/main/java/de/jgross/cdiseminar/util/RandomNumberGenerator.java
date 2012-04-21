/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.util;

import de.jgross.cdiseminar.qualifier.Random;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Jörg Groß
 */
//@RequestScoped
public class RandomNumberGenerator implements IRandomNumberGenerator {

    private int maxNumber = 100;
    private java.util.Random random =
            new java.util.Random(System.currentTimeMillis());

    java.util.Random getRandom() {
        return random;
    }

    @Produces
    @Random
    @Override
//    @SessionScoped
    public Integer next() {
        return getRandom().nextInt(maxNumber);
    }
}
