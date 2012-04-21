/* 
 * Copyright by Jörg Groß.
 */ 
package de.jgross.cdiseminar.service;

import de.jgross.cdiseminar.domain.User;
import de.jgross.cdiseminar.qualifier.Current;
import de.jgross.cdiseminar.qualifier.Persistent;
import de.jgross.cdiseminar.qualifier.Transient;
import de.jgross.cdiseminar.qualifier.UserSpecific;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author ext_gross
 */
public class ShoppingCartFactory {
    
    @Inject @Current
    private User user;
    
    @Produces @UserSpecific @ConversationScoped
    public IShoppingCartService createShoppingCart(@Transient IShoppingCartService aTransientShopCart, 
            @Persistent IShoppingCartService aPersistentShopCart) {
        
        if (user.getName().startsWith("j")) {
            System.out.println("ShoppingCartFactory: create PERSISTENT shopping cart service");
            return aPersistentShopCart;
        }
        else {
            System.out.println("ShoppingCartFactory: create TRANSIENT shopping cart service");
            return aTransientShopCart;
        }
    }
    
    public void dispose(@Disposes @UserSpecific IShoppingCartService aCart) {
        aCart.dispose();
    }
}
