/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base;

import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pintu
 */
public class CreateQueryType {

    public static <S> Query getNamedQuery(Session session, String queryStr, Class<S> cls) {
        return Objects.nonNull(cls) ? session.createNamedQuery(queryStr, cls) : session.createNamedQuery(queryStr);
    }

    public static <S> Query getQuery(Session session, String queryStr, Class<S> cls) {
        return Objects.nonNull(cls) ? session.createQuery(queryStr, cls) : session.createQuery(queryStr);
    }

    public static <S> Query getNativeQuery(Session session, String queryStr, Class<S> cls) {
        return Objects.nonNull(cls) ? session.createNativeQuery(queryStr, cls) : session.createNativeQuery(queryStr);
    }
}
