/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base.function;

import com.imos.sample.base.exception.RepositoryException;

/**
 *
 * @author ameher
 * @param <S>
 * @param <D>
 */
@FunctionalInterface
public interface BiConsumerWithException<S, D> {

    void apply(S session, D data) throws RepositoryException;
}
