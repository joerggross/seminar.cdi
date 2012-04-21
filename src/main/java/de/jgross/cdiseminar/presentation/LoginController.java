/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.presentation;

import de.jgross.cdiseminar.domain.User;
import de.jgross.cdiseminar.event.LoginEvent;
import de.jgross.cdiseminar.qualifier.Current;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Login controller.
 * 
 * @author Jörg Groß
 */
@Named("loginController")
@RequestScoped
public class LoginController implements Serializable {

    /**
     * the event producer.
     */
    @Inject
    private Event<LoginEvent> eventHandler;
    /**
     * user data model for gui binding.
     */
    private User user = new User();

    /**
     * action listener: the login action.
     * @return navigation string
     */
    public String login() {

        // fire login event.
        eventHandler.fire(new LoginEvent(this.user));
        System.out.println("LoginController: fired LoginEvent");

        // always return success, everbody may login
        return "Success";
    }

    /**
     * @return the current user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Produces the current user.
     * @return the current user.
     */
    @Produces
    @Current
    public User getCurrentUser() {
        System.out.println("LoginController: Produce @Current User");
        return this.getUser();
    }
}
