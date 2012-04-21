/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgros.eercp.remote.hessian.RemoteCallable;
import de.jgross.cdiseminar.domain.IDGenerator;
import de.jgross.cdiseminar.domain.Pet;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jörg Groß
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless(name="ejb/PetService")
@RemoteCallable(type=IPetService.class, url="/petService")
public class PetService implements IPetService{
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void delete(Pet aPet) {
        em.remove(aPet);
    }

    @Override
    public void delete(String aUniqueId) {
        em.remove(em.find(Pet.class, aUniqueId));
    }

    @Override
    public Pet get(String aUniqueId) {
        return em.find(Pet.class, aUniqueId);
    }
    
    @Override
    public Pet update(Pet aPet) {
        if (aPet.getId() == null) {
           aPet.setId(IDGenerator.generateUUIDFor(Pet.class));
        }
        return em.merge(aPet);
    }
    
    @Override
    public List<Pet> getAll() {
        Query query = em.createQuery("SELECT p FROM Pet p");
        return (List<Pet>) query.getResultList();
    }
}
