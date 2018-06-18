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
 * @param <I>
 * @param <O>
 */
@FunctionalInterface
public interface FunctionWithException<I, O> {

    O apply(I data) throws RepositoryException;
}
