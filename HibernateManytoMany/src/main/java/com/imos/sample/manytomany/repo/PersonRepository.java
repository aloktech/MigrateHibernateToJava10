/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.manytomany.repo;

import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.base.repo.AbstractRepository;
import com.imos.sample.manytomany.model.Event;
import com.imos.sample.manytomany.model.Person;
import com.imos.sample.manytomany.model.PersonAddress;
import com.imos.sample.manytomany.model.support.PersonInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author ameher
 */
@Log4j2
public class PersonRepository extends AbstractRepository {

    public List<Person> findAllPersons() {
        List<Person> persons = new ArrayList<>();
        try {
            persons = extractListAsResult("from Person p", Person.class);
        } catch (RepositoryException ex) {
            log.error("{}", ex.getMessage());
        }
        return persons;
    }

    public Optional<PersonInfo> findPersonByFirstName(String firstName) {
        Optional<PersonInfo> person = Optional.empty();
        try {
            addParameter("firstName", firstName);
            person = extractUniqueResult("select new com.imos.sample.manytomany.model.support.PersonInfo("
                    + "pd.firstName, pd.middleName, pd.lastName, pd.dateOfBirth, pd.mobileNumber, pd.email) "
                    + "from Person p "
                    + "inner join p.personDetail pd "
                    + "where pd.firstName = :firstName", PersonInfo.class);
        } catch (RepositoryException ex) {
            log.error("{} {}", ex.getMessage(), ex.getCauseOfException());
        }
        return person;
    }
    
    public List<PersonAddress> findAllAddressOfPersonByFirstName(String firstName) {
        List<PersonAddress> persons = new ArrayList<>();
        try {
            addParameter("firstName", firstName);
            persons = extractListAsResult("select a from Person p "
                    + "inner join p.addresses a "
                    + "inner join p.personDetail pd "
                    + "where pd.firstName = :firstName", PersonAddress.class);
        } catch (RepositoryException ex) {
            log.error("{}", ex.getMessage());
        }
        return persons;
    }
    
    public Optional<PersonAddress> findAddressOfPersonByFirstNameAndCity(String firstName, String cityName) {
        Optional<PersonAddress> address = Optional.empty();
        try {
            addParameter("firstName", firstName);
            addParameter("city", cityName);
            address = extractUniqueResult("select a from Person p "
                    + "inner join p.addresses a "
                    + "inner join p.personDetail pd "
                    + "where pd.firstName = :firstName and "
                    + "a.city = :city", PersonAddress.class);
        } catch (RepositoryException ex) {
            log.error("{}", ex.getMessage());
        }
        return address;
    }
    
    public List<Event> findEventOfPersonByFirstNameAndDate(String firstName, LocalDate eventDate) {
        List<Event> events = new ArrayList<>();
        try {
            addParameter("firstName", firstName);
            addParameter("eventDate", eventDate);
            events = extractListAsResult("select e from Person p "
                    + "inner join p.personDetail pd "
                    + "inner join p.events e "
                    + "where pd.firstName = :firstName and "
                    + "e.dateOfEvent = :eventDate ", Event.class);
        } catch (RepositoryException ex) {
            log.error("{}", ex.getMessage());
        }
        return events;
    }
}
