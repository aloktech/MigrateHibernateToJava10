/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base.repo;

import com.imos.sample.base.model.IData;
import com.imos.sample.base.exception.RepositoryException;

/**
 *
 * @author ameher
 * @param <T>
 */
public interface IRepository<T extends IData> {

    T getById(Class<T> cls, long id) throws RepositoryException;
    
    void addData(T data) throws RepositoryException;
    
    void addData(T... data) throws RepositoryException;

    void updateData(T data) throws RepositoryException;
    
    void updateData(T... data) throws RepositoryException;

    void deleteData(T data) throws RepositoryException;
    
    void deleteData(T... data) throws RepositoryException;
}
