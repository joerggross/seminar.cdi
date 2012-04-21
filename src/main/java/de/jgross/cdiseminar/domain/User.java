/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.domain;

import java.io.Serializable;

/**
 *
 * @author Jörg Groß
 */
public class User implements Serializable {
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
