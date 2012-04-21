/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.decorator;

import de.jgross.cdiseminar.domain.ActionLogEntry;
import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;
import de.jgross.cdiseminar.qualifier.Persistent;
import de.jgross.cdiseminar.service.IActionLogService;
import de.jgross.cdiseminar.service.IShoppingCartService;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

/**
 * Decorates the ShoppingCartService
 * Adds entries to the action log.
 * <p>
 * @author ext_gross
 */
@Decorator
public class ShoppingCartActionLogDecorator implements IShoppingCartService{
    
    /**
     * The decorated service.
     */
    @Inject @Delegate @Persistent
    IShoppingCartService shoppingCartService;

    /**
     * the action log service.
     */
    @Inject
    IActionLogService actionLogService;

    /**
     * {@inheritDoc}
     * <p>
     * Create an 'add'-action log entry.
     */
    @Override
    public ShoppingCart addPet(ShoppingCart aShoppingCart, Pet aPet) {
        this.actionLogService.update(
                ActionLogEntry.create(aShoppingCart, aPet, "Add (Decorator)"));
        return this.shoppingCartService.addPet(aShoppingCart, aPet);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Create an 'remove'-action log entry.
     */
    @Override
    public ShoppingCart removePet(ShoppingCart aShoppingCart, Pet aPet) {
        this.actionLogService.update(
                ActionLogEntry.create(aShoppingCart, aPet, "Remove (Decorator)"));
        return shoppingCartService.removePet(aShoppingCart, aPet);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(ShoppingCart aShoppingCart) {
        shoppingCartService.delete(aShoppingCart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String aUniqueId) {
                shoppingCartService.delete(aUniqueId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        shoppingCartService.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCart findByUser(String aName) {
        return shoppingCartService.findByUser(aName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCart get(String aUniqueId) {
        return shoppingCartService.get(aUniqueId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCart update(ShoppingCart aShoppingCart) {
        return shoppingCartService.update(aShoppingCart);
    }
}
