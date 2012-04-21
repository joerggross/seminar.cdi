/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.presentation;

import de.jgross.cdiseminar.domain.ActionLogEntry;
import de.jgross.cdiseminar.domain.ShoppingCart;
import de.jgross.cdiseminar.domain.User;
import de.jgross.cdiseminar.event.LoginEvent;
import de.jgross.cdiseminar.event.LogoutEvent;
import de.jgross.cdiseminar.event.ShoppingCartEvent;
import de.jgross.cdiseminar.qualifier.Add;
import de.jgross.cdiseminar.qualifier.Current;
import de.jgross.cdiseminar.qualifier.Remove;
import de.jgross.cdiseminar.qualifier.UserSpecific;
import de.jgross.cdiseminar.service.IActionLogService;
import de.jgross.cdiseminar.service.IShoppingCartService;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Provides user state.
 * <p>
 * @author Jörg Groß
 */
//@SessionScoped
@ConversationScoped
public class UserManagement implements Serializable {

    @Inject
    Conversation conversation;
    /**
     * shopping cart service
     */
    @Inject
    //@Persistent
    //@Transient
    @UserSpecific
    private IShoppingCartService shoppingCartService;
    /**
     * action log service
     */
    @Inject
    IActionLogService actionLogService;
    /**
     * the current user
     */
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Event consumer. Starts user session/conversation.
     * <p>
     * Creates new cart if necessary.
     * <p>
     * @param event 
     */
    public void onLogin(@Observes LoginEvent event) {

        conversation.begin();
        System.out.println("UserManagement: conversation.begin()");
        this.currentUser = event.getUser();

        ShoppingCart shoppingCart = this.shoppingCartService.findByUser(
                this.getCurrentUser().getName());
        
        if (shoppingCart == null) {
            System.out.println("UserManagement: create new cart for: " 
                    + this.getCurrentUser().getName());
            shoppingCart = new ShoppingCart();
            shoppingCart.setUserName(this.getCurrentUser().getName());
            shoppingCart = shoppingCartService.update(shoppingCart);
        }
    }

    /**
     * Event consumer. Logs out the curremt user, release state
     * associated with curremt user (session or conversation).
     * <p>
     * @param event 
     */
    public void requestlogout(@Observes LogoutEvent event) {

        conversation.end();
        System.out.println("UserManagement: conversation.end()");

        // invalidate session
//        FacesContext fc = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
    }

    /**
     * Event consumer. Creates an action log entry for add-Event.
     * <p>
     * @param event 
     */
    public void onAddToCart(
            @Observes(during = TransactionPhase.AFTER_SUCCESS)
            @Add ShoppingCartEvent event) {
        
        System.out.println("ShopController: onAddToCart: "
                + event.getPet().getName());
        
        this.actionLogService.update(
                ActionLogEntry.create(event.getShoppingCart(),
                event.getPet(),
                "Add (Event)"));
    }

    /**
     * Event consumer. Creates an action log entry for remove-Event.
     * <p>
     * @param event 
     */
    public void onRemoveFromCart(
            @Observes(during = TransactionPhase.AFTER_SUCCESS) 
            @Remove ShoppingCartEvent event) {
        
        System.out.println("ShopController: onRemoveFromCart: " 
                + event.getPet().getName());
        
        this.actionLogService.update(
                ActionLogEntry.create(event.getShoppingCart(), 
                event.getPet(), 
                "Remove (Event)"));
    }
}
