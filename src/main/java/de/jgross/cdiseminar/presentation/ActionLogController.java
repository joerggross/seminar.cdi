/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.presentation;

import de.jgross.cdiseminar.domain.ActionLogEntry;
import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;
import de.jgross.cdiseminar.domain.User;
import de.jgross.cdiseminar.event.LogoutEvent;
import de.jgross.cdiseminar.qualifier.Persistent;
import de.jgross.cdiseminar.service.IActionLogService;
import de.jgross.cdiseminar.service.IShoppingCartService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * this controller serves action log page.
 * <p>
 * @author Jörg Groß
 */
@Named("actionLogController")
@RequestScoped
public class ActionLogController implements Serializable {
    

    /**
     * shopping cart service
     */
    @Inject
    private IActionLogService actionLogService;

    // **********************************************
    //  data accessors
    // **********************************************
    public List<ActionLogEntry> getActionLogEntries() {
        return this.actionLogService.getAll();
    }

}
