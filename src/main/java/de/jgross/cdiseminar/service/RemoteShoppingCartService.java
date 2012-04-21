/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgros.eercp.remote.hessian.RemoteCallable;
import de.jgross.cdiseminar.domain.IDGenerator;
import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.ShoppingCart;
import de.jgross.cdiseminar.qualifier.Persistent;
import de.jgross.cdiseminar.qualifier.Remote;
import java.lang.Override;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jörg Groß
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless(name = "ejb/ShoppingCartService")
@RemoteCallable(type = IShoppingCartService.class, url = "/shoppingCartService")
@Remote
//@Persistent
public class RemoteShoppingCartService implements IShoppingCartService {

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
    public ShoppingCart addPet(ShoppingCart aShoppingCart, Pet aPet) {
        aShoppingCart.addPet(aPet);
        return this.update(aShoppingCart);
    }

    @Override
    public ShoppingCart removePet(ShoppingCart aShoppingCart, Pet aPet) {
        aShoppingCart.removePet(aPet);
        return this.update(aShoppingCart);
    }

    @Override
    public void dispose() {
        System.out.println("Dispose REMOTE-PERSISTENT shoppig cart service");
    }
}
