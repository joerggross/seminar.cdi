/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgros.eercp.remote.hessian.RemoteCallable;
import de.jgross.cdiseminar.domain.ActionLogEntry;
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
@Stateless
public class ActionLogService implements IActionLogService{
    
    @PersistenceContext
    private EntityManager em;

   
    @Override
    public ActionLogEntry update(ActionLogEntry anActionLogEntry) {
        if (anActionLogEntry.getId() == null) {
           anActionLogEntry.setId(IDGenerator.generateUUIDFor(ActionLogEntry.class));
        }
        return em.merge(anActionLogEntry);
    }
    
    @Override
    public List<ActionLogEntry> getAll() {
        Query query = em.createQuery("SELECT a FROM ActionLogEntry a");
        return (List<ActionLogEntry>) query.getResultList();
    }
}
