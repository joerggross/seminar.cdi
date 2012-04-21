/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Jörg Groß
 */
@Entity
public class ShoppingCart implements Serializable {
    
    @Id
    String id;
    
    String userName;
    
    @OneToMany(fetch= FetchType.EAGER)
    private List<Pet> pets = new ArrayList<Pet>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public void removePet(Pet pet) {
        this.pets.remove(pet);
    }
    
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

  
    
}
