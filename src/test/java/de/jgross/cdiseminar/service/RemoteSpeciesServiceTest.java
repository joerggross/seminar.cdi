/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import com.caucho.hessian.client.HessianProxyFactory;
import de.jgross.cdiseminar.domain.Species;
import java.net.MalformedURLException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the remote access of the specie service.
 * <p>
 * @author Jörg Groß
 */
public class RemoteSpeciesServiceTest {

    private ISpeciesService remoteSpeciesService;

    public RemoteSpeciesServiceTest() {
    }

    /**
     * Retrieve remote bean reference.
     * <p>
     * @throws MalformedURLException 
     */
    @Before
    public void setUp() throws MalformedURLException {

        String url = "http://localhost:8080/cdi/speciesService";
        HessianProxyFactory factory = new HessianProxyFactory();
        this.remoteSpeciesService = (ISpeciesService) factory.create(ISpeciesService.class, url);
    }

    /**
     * Test of update method, of class RemotePersonService.
     */
    @Test
    public void testUpdateSpecies() {

        Species species = new Species();
        species.setName("Hund");
        species = this.remoteSpeciesService.update(species);

        species = new Species();
        species.setName("Katze");
        species = this.remoteSpeciesService.update(species);

        species = new Species();
        species.setName("Maus");
        species = this.remoteSpeciesService.update(species);

        species = new Species();
        species.setName("Fisch");
        species = this.remoteSpeciesService.update(species);

    }
}
