/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.event;

import de.jgross.cdiseminar.domain.User;
import java.io.Serializable;

/**
 *
 * @author Jörg Groß
 */
public class LoginEvent implements Serializable {
    
    private User user;

    public LoginEvent(User aUser) {
        this.user = aUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
