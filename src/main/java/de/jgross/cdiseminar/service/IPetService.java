/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgross.cdiseminar.domain.Pet;
import java.util.List;

/**
 *
 * @author Jörg Groß
 */
public interface IPetService {
        
    public Pet get(String aUniqueId);
    
    public Pet update(Pet aPet);

    public void delete(Pet aPet);

    public void delete(String aUniqueId);
   
    public List<Pet> getAll();
}
