/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;

/**
 *
 * @author Jörg Groß
 */
public interface IShoppingCartService {

    public ShoppingCart get(String aUniqueId);

    public ShoppingCart update(ShoppingCart aShoppingCart);

    public ShoppingCart addPet(ShoppingCart aShoppingCart, Pet aPet);

    public ShoppingCart removePet(ShoppingCart aShoppingCart, Pet aPet);

    public void delete(ShoppingCart aShoppingCart);

    public void delete(String aUniqueId);

    public ShoppingCart findByUser(String aName);
    
    public void dispose();
}
