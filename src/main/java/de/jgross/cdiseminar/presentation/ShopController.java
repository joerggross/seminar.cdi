/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.presentation;

import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;
import de.jgross.cdiseminar.domain.User;
import de.jgross.cdiseminar.event.LoginEvent;
import de.jgross.cdiseminar.event.LogoutEvent;
import de.jgross.cdiseminar.qualifier.UserSpecific;
import de.jgross.cdiseminar.service.IPetService;
import de.jgross.cdiseminar.service.IShoppingCartService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * ShopController serves the petlist page.
 * <p>
 * @author Jörg Groß
 */
@Named("shopController")
@RequestScoped
public class ShopController implements Serializable {

    /**
     * Logout event producer
     */
    @Inject
    Event<LogoutEvent> logoutEventProducer;
    /**
     * pet service
     */
    @EJB
    private IPetService petService;
    /**
     * Session State user management
     */
    @Inject
    UserManagement userManagement;
    /**
     * shopping cart service
     */
    @Inject
    //@Persistent
    //@Transient
    @UserSpecific
    private IShoppingCartService shoppingCartService;
    /**
     * table data model pets to buy.
     */
    private DataModel<Pet> petsTableModel = null;
    /**
     * current shopping cart
     */
    private ShoppingCart shoppingCart;
    /**
     * pet list.
     */
    private List<Pet> pets;

    // **********************************************
    //  data accessors
    // **********************************************
    public DataModel<Pet> getPetsTableModel() {
        List<Pet> tempPetList = this.getPets();
        tempPetList.removeAll(this.getShoppingCart().getPets());
        this.petsTableModel = new ListDataModel<Pet>(tempPetList);
        return petsTableModel;
    }

    public List<Pet> getPets() {
        if (pets == null) {
            pets = this.petService.getAll();
        }
        return pets;
    }

    public ShoppingCart getShoppingCart() {
        if (this.shoppingCart == null) {
            this.shoppingCart =
                    this.shoppingCartService.findByUser(
                    this.getUser().getName());
        }
        return shoppingCart;
    }

    public User getUser() {
        return userManagement.getCurrentUser();
    }

    // **********************************************
    //  action handler 
    // **********************************************
    public String addToCart() {
        Pet current = petsTableModel.getRowData();
        shoppingCartService.addPet(this.getShoppingCart(), current);
        return "List";
    }

    public String showCart() {
        return "ShoppingCart";
    }

    public String logout() {
        logoutEventProducer.fire(new LogoutEvent());
        return "Login";
    }

    public String showPetList() {
        return "List";
    }

//    @Inject
//    void initServices(@Any Instance<IShoppingCartService> services) {
//        System.out.println("List ShoppingCartServices ");
//        for (IShoppingCartService service : services) {
//            System.out.println(" - " + service.getClass());
//        }
//    }

}
