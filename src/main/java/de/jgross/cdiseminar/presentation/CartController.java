/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.presentation;

import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;
import de.jgross.cdiseminar.domain.User;
import de.jgross.cdiseminar.event.LogoutEvent;
import de.jgross.cdiseminar.qualifier.UserSpecific;
import de.jgross.cdiseminar.service.IShoppingCartService;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * CartController serves shopping cart page.
 * <p>
 * @author Jörg Groß
 */
@Named("cartController")
@RequestScoped
public class CartController implements Serializable {
    
    /**
     * Logout event producer.
     */
    @Inject Event<LogoutEvent> logoutEventProducer;

    /**
     * shopping cart service
     */
    @Inject
   // @Persistent
    //@Transient
    @UserSpecific
    private IShoppingCartService shoppingCartService;

    /**
     * table data model shopping cart pets.
     */
    private DataModel<Pet> shoppingCartPets = null;
    
    /**
     * current shopping cart
     */
    private ShoppingCart shoppingCart;
    
    /**
     * Session state
     */
    @Inject
    UserManagement userManagement;

    // **********************************************
    //  data accessors
    // **********************************************
    public DataModel<Pet> getShoppingCartPets() {
        if (this.shoppingCartPets == null) {
            this.shoppingCartPets = 
                    new ListDataModel<Pet>(this.getShoppingCart().getPets());
        }
        return shoppingCartPets;
    }

    public ShoppingCart getShoppingCart() {
        if (this.shoppingCart == null) {
            this.shoppingCart =
                    this.shoppingCartService.findByUser(this.getUser().getName());
        }
        return shoppingCart;
    }

    public User getUser() {
        return this.userManagement.getCurrentUser();
    }

    // **********************************************
    //  action handler 
    // **********************************************
    public String removeFromCart() {
        Pet current = shoppingCartPets.getRowData();
        shoppingCartService.removePet(this.getShoppingCart(), current);
        return "ShoppingCart";
    }

    public String logout() {
        logoutEventProducer.fire(new LogoutEvent());
        return "Login";
    }

    public String showPetList() {
        return "List";
    }
}
