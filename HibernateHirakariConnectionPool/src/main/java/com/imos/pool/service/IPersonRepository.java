/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.pool.service;

import com.imos.base.exception.RepositoryException;
import com.imos.pool.model.Person;
import java.util.List;

/**
 *
 * @author ameher
 */
public interface IPersonRepository {

    List<Person> findAllPersons() throws RepositoryException;
}
