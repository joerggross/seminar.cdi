/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.domain;

import java.util.UUID;

/**
 *
 * @author Jörg Groß
 */
public class IDGenerator {
    
    public static String generateUUIDFor(Class aType) {
        return UUID.randomUUID().toString();
    }
    
}
