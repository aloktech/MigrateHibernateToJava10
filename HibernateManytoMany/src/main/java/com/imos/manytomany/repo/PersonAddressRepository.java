/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.manytomany.repo;

import com.imos.base.exception.RepositoryException;
import com.imos.base.repo.AbstractRepository;
import com.imos.manytomany.model.PersonAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ameher
 */
public class PersonAddressRepository extends AbstractRepository {

    public List<PersonAddress> getAllPersonAddresss() {
        List<PersonAddress> persons = new ArrayList<>();
        try {
            persons = extractListAsResult("from Person p", PersonAddress.class);
        } catch (RepositoryException ex) {
            Logger.getLogger(PersonRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons;
    }
}
