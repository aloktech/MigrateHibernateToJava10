/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.base.function;

import com.imos.base.exception.QueryTypeException;

/**
 *
 * @author pintu
 * @param <T>
 * @param <U>
 * @param <V>
 */
@FunctionalInterface
public interface BiFunctionWithException<T, U, V> {

    V apply(T t, U u) throws QueryTypeException;
}
