/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.manytomany.repo;

import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.base.repo.AbstractRepository;
import com.imos.sample.manytomany.model.PersonDetail;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ameher
 */
public class PersonDetailRepository extends AbstractRepository {

    public List<PersonDetail> getAllPersonDetails() {
        List<PersonDetail> persons = new ArrayList<>();
        try {
            persons = extractListAsResult("from Person p", PersonDetail.class);
        } catch (RepositoryException ex) {
            Logger.getLogger(PersonRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons;
    }
}
