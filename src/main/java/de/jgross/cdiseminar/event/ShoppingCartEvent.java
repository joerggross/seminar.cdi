/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.event;

import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;
import java.io.Serializable;

/**
 *
 * @author Jörg Groß
 */
public class ShoppingCartEvent implements Serializable {

    private Pet pet;
    
    private ShoppingCart shoppingCart;

    public ShoppingCartEvent(Pet aPet, ShoppingCart shoppingCart) {
        this.pet = aPet;
        this.shoppingCart = shoppingCart;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
    
}
