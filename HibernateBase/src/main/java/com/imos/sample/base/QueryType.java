/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base;

import com.imos.sample.base.function.TriFunctionWithException;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Pintu
 */
public enum QueryType {
    
    NAMED_JPA_QUERY(CreateQueryType::getNamedQuery),
    JPA_QUERY(CreateQueryType::getQuery),
    NATIVE_QUERY(CreateQueryType::getNativeQuery);

    @Getter
    private final  TriFunctionWithException<Session, String, Class, Query> dataType;

    private QueryType(TriFunctionWithException<Session, String, Class, Query> dataType) {
        this.dataType = dataType;
    }
}
