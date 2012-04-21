/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgross.cdiseminar.domain.IDGenerator;
import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;
import de.jgross.cdiseminar.qualifier.Transient;
import java.io.Serializable;
import java.lang.Override;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Jörg Groß
 */
@Transient
//@SesssionScoped
public class TransientShoppingCartService implements IShoppingCartService, Serializable {

    Map<String, ShoppingCart> shopMap = new HashMap<String, ShoppingCart>();

    @Override
    public void delete(ShoppingCart aShoppingCart) {
        shopMap.remove(aShoppingCart.getId());
    }

    @Override
    public void delete(String aUniqueId) {
        shopMap.remove(aUniqueId);
    }

    @Override
    public ShoppingCart get(String aUniqueId) {
        return shopMap.get(aUniqueId);
    }

    @Override
    public ShoppingCart update(ShoppingCart aShoppingCart) {
        if (aShoppingCart.getId() == null) {
            aShoppingCart.setId(IDGenerator.generateUUIDFor(ShoppingCart.class));
        }
        shopMap.put(aShoppingCart.getId(), aShoppingCart);
        return aShoppingCart;
    }

    @Override
    public ShoppingCart addPet(ShoppingCart aShoppingCart, Pet aPet) {
        System.out.println("TransientShoppingCartService.addPet()");
        aShoppingCart.addPet(aPet);
        return this.update(aShoppingCart);
    }
    
    @Override
    public ShoppingCart removePet(ShoppingCart aShoppingCart, Pet aPet) {
        aShoppingCart.removePet(aPet);
        return this.update(aShoppingCart);
    }

    @Override
    public ShoppingCart findByUser(String aName) {
        for (ShoppingCart cart : shopMap.values()) {
            if (aName.equals(cart.getUserName())) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public void dispose() {
        System.out.println("Dispose TRANSIENT shoppig cart service");
    }
}
