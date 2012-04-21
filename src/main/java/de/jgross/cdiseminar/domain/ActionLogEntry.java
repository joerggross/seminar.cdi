/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Jörg Groß
 */
@Entity
public class ActionLogEntry implements Serializable {
    
    @Id
    String id;
       
    String actionType;
    
    String userName;
    
    String petName;

    public String getId() {
        return id;
    }
    
    public static ActionLogEntry create(ShoppingCart aShoppingCart, Pet aPet, String anActionType) {
        ActionLogEntry entry = new ActionLogEntry();
        entry.setActionType(anActionType);
        entry.setPetName(aPet.getName());
        entry.setUserName(aShoppingCart.getUserName());
        return entry;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionLogEntry other = (ActionLogEntry) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
}
