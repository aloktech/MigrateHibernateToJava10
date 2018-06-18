/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.onetoone.test;

import com.imos.sample.base.HibernateService;
import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.base.model.IData;
import com.imos.sample.onetoone.model.Person;
import com.imos.sample.onetoone.model.PersonDetail;
import com.imos.sample.onetoone.model.support.PersonInfo;
import com.imos.sample.onetoone.repo.PersonDetailRepository;
import com.imos.sample.onetoone.repo.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SampleOneToOneTest {

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

//    @Disabled
    @Test
    public void personAdd() {
        Person person = new Person();

        PersonRepository repo = new PersonRepository();
        try {
            List<? extends IData> persons = repo.findAllPersons();
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
            List<? extends IData> persons = repo.findAllPersons();
            assertFalse(persons.isEmpty());

            person = (Person) persons.get(0);

            assertNotNull(person.getId());

            persons = repo.findAllPersons();
            assertFalse(persons.isEmpty());

            person = (Person) persons.get(0);

            assertNotNull(person.getId());

            repo.deleteData(person);

            persons = repo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
    }

//    @Disabled
    @Test
    public void personDetailAdd() {
        PersonDetail personDetail = new PersonDetail();

        PersonDetailRepository repo = new PersonDetailRepository();
        try {
            List<PersonDetail> persons = repo.findAllPersonDetail();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            repo.addData(personDetail);
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            List<PersonDetail> persons = repo.findAllPersonDetail();
            assertFalse(persons.isEmpty());

            personDetail = persons.get(0);

            assertNotNull(personDetail.getId());

            persons = repo.findAllPersonDetail();
            assertFalse(persons.isEmpty());

            personDetail = persons.get(0);

            assertNotNull(personDetail.getId());

            repo.deleteData(personDetail);

            persons = repo.findAllPersonDetail();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
    }

//    @Disabled
    @Test
    public void personAndPersonDetailCheck() {
        Person personA = new Person();

        PersonDetail personDetailA = new PersonDetail();
        personDetailA.setFirstName("Alok");

        personA.setPersonDetail(personDetailA);
        personDetailA.setPerson(personA);

        PersonRepository personRepo = new PersonRepository();
        PersonDetailRepository personDetailRepo = new PersonDetailRepository();

        try {
            List<? extends IData> persons = personRepo.findAllPersons();
            assertTrue(persons.isEmpty());
            List<PersonDetail> personDetails = personDetailRepo.findAllPersonDetail();
            assertTrue(personDetails.isEmpty());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            personRepo.addData(personA, personDetailA);
            log.info("Person and PersonDetail are added");
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            List<? extends IData> persons = personRepo.findAllPersons();
            assertFalse(persons.isEmpty());
            personA = (Person) persons.get(0);
            log.info("Got the Person");
            assertNotNull(personA.getId());
            personDetailA = personA.getPersonDetail();
            log.info("Got the PersonDetail");
            assertNotNull(personDetailA.getId());
            assertEquals("Alok", personA.getPersonDetail().getFirstName());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            personRepo.deleteData(personA, personDetailA);
        } catch (RepositoryException ex) {
            Logger.getLogger(SampleOneToOneTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            List<? extends IData> persons = personRepo.findAllPersons();
            assertTrue(persons.isEmpty());
            List<PersonDetail> personDetails = personDetailRepo.findAllPersonDetail();
            assertTrue(personDetails.isEmpty());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
    }

//    @Disabled
    @Test
    public void findPersonByName() {
        Person personA = new Person();

        PersonDetail personDetailA = new PersonDetail();
        personDetailA.setFirstName("Alok");

        personA.setPersonDetail(personDetailA);
        personDetailA.setPerson(personA);

        PersonRepository personRepo = new PersonRepository();
        PersonDetailRepository personDetailRepo = new PersonDetailRepository();

        try {
            List<? extends IData> persons = personRepo.findAllPersons();
            assertTrue(persons.isEmpty());
            List<PersonDetail> personDetails = personDetailRepo.findAllPersonDetail();
            assertTrue(personDetails.isEmpty());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            personRepo.addData(personA, personDetailA);
            log.info("Person and PersonDetail are added");
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            Optional<PersonInfo> personOpt = personRepo.findPersonInfoByFirstName("Alok");
            assertTrue(personOpt.isPresent());
            if (personOpt.isPresent()) {
                PersonInfo info = personOpt.get();
                assertEquals("Alok", info.getFirstName());
            }
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }
        try {
            personA = personRepo.findPersonByFirstName("Alok").get();
            personRepo.deleteData(personA, personA.getPersonDetail());

            List<? extends IData> persons = personRepo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
        }

    }
}
