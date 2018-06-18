/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.onetomany.test;

import com.imos.sample.base.HibernateService;
import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.onetomany.model.Person;
import com.imos.sample.onetomany.model.PersonAddress;
import com.imos.sample.onetomany.model.PersonDetail;
import com.imos.sample.onetomany.model.support.PersonInfo;
import com.imos.sample.onetomany.repo.PersonRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ameher
 */
@Log4j2
public class SampleOneToManyTest {

    @BeforeAll
    public static void setUp() {
        try {
            HibernateService.INSTANCE.config("test.hibernate.cfg.xml");
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
    }

    @AfterAll
    public static void shutDown() {
        try {
            HibernateService.INSTANCE.shutDown();
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
    }

    @Test
    public void addPersonCheck() {
        Person person = new Person();

        PersonDetail personDetail = new PersonDetail();
        personDetail.setFirstName("Alok");

        person.setPersonDetail(personDetail);
        personDetail.setPerson(person);

        PersonAddress address1 = new PersonAddress();
        address1.setCity("Bengaluru");

        person.getAddresses().add(address1);
        address1.setPerson(person);

        PersonAddress address2 = new PersonAddress();
        address2.setCity("Balangir");

        person.getAddresses().add(address2);
        address2.setPerson(person);

        PersonRepository repo = new PersonRepository();
        try {
            repo.addData(person, personDetail, address1, address2);
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }

        List<Person> persons = repo.findAllPersons();
        assertFalse(persons.isEmpty());
        assertEquals(1, persons.size());
        
        Optional<PersonInfo> personInfoOp = repo.findPersonByFirstName("Alok");
        assertTrue(personInfoOp.isPresent());
        PersonInfo personInfo = personInfoOp.get();
        assertEquals("Alok", personInfo.getFirstName());
        
        List<PersonAddress> addresses = repo.findAllAddressOfPersonByFirstName("Alok");
        assertFalse(addresses.isEmpty());
        assertEquals(2, addresses.size());
        List<String> cities = Arrays.asList("Bengaluru", "Balangir");
        assertTrue(cities.contains(addresses.get(0).getCity()));
        assertTrue(cities.contains(addresses.get(1).getCity()));
        
        Optional<PersonAddress> personAddressOp = repo.findAddressOfPersonByFirstNameAndCity("Alok", "Bengaluru");
        assertTrue(personAddressOp.isPresent());
        address1 = personAddressOp.get();
        assertEquals("Bengaluru", address1.getCity());
        assertNull(address1.getCountry());
        
        personAddressOp = repo.findAddressOfPersonByFirstNameAndCity("Alok", "Balangir");
        assertTrue(personAddressOp.isPresent());
        address1 = personAddressOp.get();
        assertEquals("Balangir", address1.getCity());
        assertNull(address1.getCountry());
    }
}
