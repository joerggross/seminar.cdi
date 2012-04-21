/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgross.cdiseminar.domain.IDGenerator;
import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;
import java.lang.Override;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jörg Groß
 */
public class ShoppingCartService implements IShoppingCartService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void delete(ShoppingCart aShoppingCart) {
        em.remove(aShoppingCart);
    }

    @Override
    public void delete(String aUniqueId) {
        em.remove(em.find(ShoppingCart.class, aUniqueId));
    }

    @Override
    public ShoppingCart get(String aUniqueId) {
        return em.find(ShoppingCart.class, aUniqueId);
    }

    @Override
    public ShoppingCart update(ShoppingCart aShoppingCart) {
        if (aShoppingCart.getId() == null) {
            aShoppingCart.setId(IDGenerator.generateUUIDFor(ShoppingCart.class));
        }
        return em.merge(aShoppingCart);
    }

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

    @Override
    public void dispose() {
    }

    @Override
    //@RequireTx
    public ShoppingCart addPet(ShoppingCart aShoppingCart, Pet aPet) {
        aShoppingCart.addPet(aPet);
        return this.update(aShoppingCart);
    }

    @Override
    //@RequireTx
    public ShoppingCart removePet(ShoppingCart aShoppingCart, Pet aPet) {
        aShoppingCart.removePet(aPet);
        return this.update(aShoppingCart);
    }
}
