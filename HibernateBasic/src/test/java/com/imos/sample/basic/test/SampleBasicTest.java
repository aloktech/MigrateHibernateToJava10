/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.basic.test;

import com.imos.sample.base.HibernateService;
import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.basic.model.Person;
import com.imos.sample.basic.repo.PersonRepository;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ameher
 */
@Log4j2
public class SampleBasicTest {

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
    public void personAdd() {
        Person person = new Person();
        person.setFirstName("Alok");

        PersonRepository repo = new PersonRepository();
        try {
            List<Person> persons = repo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            repo.addData(person);
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            List<Person> persons = repo.findAllPersons();
            assertFalse(persons.isEmpty());

            person = persons.get(0);

            assertEquals("Alok", person.getFirstName());
            assertNotNull(person.getId());

            person.setFirstName("Pintu");

            repo.updateData(person);

            persons = repo.findAllPersons();
            assertFalse(persons.isEmpty());

            person = persons.get(0);

            assertEquals("Pintu", person.getFirstName());
            assertNotNull(person.getId());

            repo.deleteData(person);

            persons = repo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
    }
}
