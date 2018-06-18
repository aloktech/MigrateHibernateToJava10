/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.basic.repo.service;

import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.basic.model.Person;
import java.util.List;

/**
 *
 * @author ameher
 */
public interface IPersonRepository {

    List<Person> findAllPersons() throws RepositoryException;
}
