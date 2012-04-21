/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgross.cdiseminar.domain.IDGenerator;
import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;
import de.jgross.cdiseminar.event.ShoppingCartEvent;
import de.jgross.cdiseminar.interceptor.RequireTx;
import de.jgross.cdiseminar.qualifier.Add;
import de.jgross.cdiseminar.qualifier.Persistent;
import de.jgross.cdiseminar.qualifier.Remove;
import java.io.Serializable;
import java.lang.Override;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Provides methods to retrieve and persist ShoppinCarts.
 * <p>
 * @author Jörg Groß
 */
@Persistent
//@Stateless
public class PersistentShoppingCartService implements IShoppingCartService, Serializable {

    @PersistenceContext
    private EntityManager em;
    
    /**
     * ShoppingCartEvent-Event reference.
     */
    @Inject @Any Event<ShoppingCartEvent> addToCartEventProducer;

    /**
     *  {@inheritDoc}
     */
    @Override
    public void delete(ShoppingCart aShoppingCart) {
        em.remove(aShoppingCart);
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void delete(String aUniqueId) {
        em.remove(em.find(ShoppingCart.class, aUniqueId));
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public ShoppingCart get(String aUniqueId) {
        return em.find(ShoppingCart.class, aUniqueId);
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    @RequireTx
    public ShoppingCart update(ShoppingCart aShoppingCart) {
        if (aShoppingCart.getId() == null) {
            aShoppingCart.setId(IDGenerator.generateUUIDFor(ShoppingCart.class));
        }
        return em.merge(aShoppingCart);
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    @RequireTx
    public ShoppingCart addPet(ShoppingCart aShoppingCart, Pet aPet) {
        System.out.println("PersistentShoppingCartService.addPet()");
        aShoppingCart.addPet(aPet);
        addToCartEventProducer.select(
                new AnnotationLiteral<Add>(){}).fire(
                    new ShoppingCartEvent(aPet, aShoppingCart));
        return this.update(aShoppingCart);
    }
      
    /**
     *  {@inheritDoc}
     */
    @Override
    @RequireTx
    public ShoppingCart removePet(ShoppingCart aShoppingCart, Pet aPet) {
        aShoppingCart.removePet(aPet);
        addToCartEventProducer.select(
                new AnnotationLiteral<Remove>(){}).fire(
                    new ShoppingCartEvent(aPet, aShoppingCart));
        return this.update(aShoppingCart);
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public ShoppingCart findByUser(String aName) {
        Query query = em.createQuery("SELECT sc FROM ShoppingCart sc where sc.userName = :name");
        query.setParameter("name", aName);
        List<ShoppingCart> result = (List<ShoppingCart>) query.getResultList();
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }
    
    /**
     *  {@inheritDoc}
     */
    @Override
    public void dispose() {
        System.out.println("Dispose PERSISTENT shoppig cart service");
    }
}
