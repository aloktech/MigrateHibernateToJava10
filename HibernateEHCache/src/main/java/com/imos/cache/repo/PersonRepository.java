/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.cache.repo;

import com.imos.base.repo.AbstractRepository;
import com.imos.base.exception.RepositoryException;
import com.imos.cache.model.Person;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author ameher
 */
@Log4j2
public class PersonRepository extends AbstractRepository<Person> {

    public PersonRepository() {
    }
    
    public PersonRepository(boolean cacheable) {
        this.cacheable = cacheable;
    }

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
}
