/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import com.caucho.hessian.client.HessianProxyFactory;
import de.jgross.cdiseminar.domain.Pet;
import de.jgross.cdiseminar.domain.Species;
import java.net.MalformedURLException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the remote access of the pet service.
 * <p>
 * @author Jörg Groß
 */
public class RemotePetServiceTest {

    private IPetService remotePetService;
    private ISpeciesService remoteSpeciesService;
    
    public RemotePetServiceTest() {
    }

    /**
     * Retrieve remote bean reference.
     * <p>
     * @throws MalformedURLException 
     */
    @Before
    public void setUp() throws MalformedURLException {

        String url = "http://localhost:8080/cdi/petService";
        HessianProxyFactory factory = new HessianProxyFactory();
        this.remotePetService = (IPetService) factory.create(IPetService.class, url);
        
        url = "http://localhost:8080/cdi/speciesService";
        this.remoteSpeciesService = (ISpeciesService) factory.create(ISpeciesService.class, url);
    }

    /**
     * Test of update method, of class RemotePersonService.
     */
    @Test
    public void testUpdatePet() {

        Species dogs = this.remoteSpeciesService.findByName("Hund");
        Species cats = this.remoteSpeciesService.findByName("Katze");
        Species mice = this.remoteSpeciesService.findByName("Maus");
        Species fish = this.remoteSpeciesService.findByName("Fisch");
        Assert.assertFalse(dogs == null);
        Assert.assertFalse(cats == null);
        Assert.assertFalse(mice == null);
        Assert.assertFalse(fish == null);

        Pet pet = new Pet();
        pet.setSpecies(dogs);
        pet.setName("Doggy");
        pet = this.remotePetService.update(pet);

        pet = new Pet();
        pet.setSpecies(dogs);
        pet.setName("Fassy");
        pet = this.remotePetService.update(pet);

                pet = new Pet();
        pet.setSpecies(fish);
        pet.setName("Hay");
        pet = this.remotePetService.update(pet);

                pet = new Pet();
        pet.setSpecies(cats);
        pet.setName("Fritz");
        pet = this.remotePetService.update(pet);

        pet = new Pet();
        pet.setSpecies(mice);
        pet.setName("Sendung mit der");
        pet = this.remotePetService.update(pet);

    }
}
