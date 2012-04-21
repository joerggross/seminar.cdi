/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Jörg Groß
 */
@Entity
public class Species implements Serializable {
    
    @Id
    String id;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
