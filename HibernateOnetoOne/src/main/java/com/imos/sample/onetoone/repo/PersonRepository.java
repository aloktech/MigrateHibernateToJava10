/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.onetoone.repo;

import com.imos.sample.base.repo.AbstractRepository;
import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.onetoone.model.Person;
import com.imos.sample.onetoone.model.support.PersonInfo;
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

    public List<Person> findAllPersons() throws RepositoryException {
        List<Person> persons = new ArrayList<>();
        try {
            persons = extractListAsResult("from Person p", Person.class);
            log.info("Find all Person");
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        return persons;
    }

    public Optional<Person> findPersonByFirstName(String firstName) throws RepositoryException {
        Optional<Person> personOpt = Optional.empty();
        addParameter("firstName", firstName);
        try {
            personOpt = extractUniqueResult("select p "
                    + "from Person p "
                    + "inner join p.personDetail pd "
                    + "where pd.firstName = :firstName", Person.class);
            log.info("Find a Person with firstName = " + firstName);
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        return personOpt;
    }

    public Optional<PersonInfo> findPersonInfoByFirstName(String firstName) throws RepositoryException {
        Optional<PersonInfo> personOpt = Optional.empty();
        addParameter("firstName", firstName);
        try {
            personOpt = extractUniqueResult("select new com.imos.sample.onetoone.model.support.PersonInfo(pd.firstName, pd.lastName, pd.dateOfBirth) "
                    + "from Person p "
                    + "inner join p.personDetail pd "
                    + "where pd.firstName = :firstName", PersonInfo.class);
            log.info("Find a Person with firstName = " + firstName);
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
            throw ex;
        }

        return personOpt;
    }
}
