/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgros.eercp.remote.hessian.RemoteCallable;
import de.jgross.cdiseminar.domain.IDGenerator;
import de.jgross.cdiseminar.domain.Species;
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
@Stateless(name="ejb/SpeciesService")
@RemoteCallable(type=ISpeciesService.class, url="/speciesService")
public class SpeciesService implements ISpeciesService {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void delete(Species aSpecies) {
        em.remove(aSpecies);
    }

    @Override
    public void delete(String aUniqueId) {
        em.remove(em.find(Species.class, aUniqueId));
    }

    @Override
    public Species get(String aUniqueId) {
        return em.find(Species.class, aUniqueId);
    }
    
    @Override
    public Species findByName(String aName) {
       Query query = em.createQuery("SELECT s FROM Species s where s.name = :name");
       query.setParameter("name", aName);
       List<Species> result = (List<Species>) query.getResultList();
       if (result.size() > 0) {
           return result.get(0);
       }
      return null;
    }
    
    @Override
    public Species update(Species aSpecies) {
        if (aSpecies.getId() == null) {
           aSpecies.setId(IDGenerator.generateUUIDFor(Species.class));
        }
        return em.merge(aSpecies);
    }
    
    @Override
    public List<Species> getAll() {
        Query query = em.createQuery("SELECT s FROM Species s");
        return (List<Species>) query.getResultList();
    }
}
