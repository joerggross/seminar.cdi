/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.Species;
import java.util.List;

/**
 *
 * @author Jörg Groß
 */
public interface ISpeciesService {

    public Species get(String aUniqueId);

    public Species update(Species aSpecies);

    public void delete(Species aSpecies);

    public void delete(String aUniqueId);

    public List<Species> getAll();

    public Species findByName(String aName);
}
