/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base;

import org.hibernate.query.Query;

/**
 *
 * @author pintu
 */
public class QueryDataType {

    public static <S> QueryData<S> getSingleData(QueryData<S> queryData, Query<S> query) {
        queryData.setSingleData(query.uniqueResultOptional());
        return queryData;
    }

    public static <S> QueryData<S> getListData(QueryData<S> queryData, Query<S> query) {
        queryData.setListOfData(query.getResultList());
        return queryData;
    }
    
    public static <S> QueryData<S> getOtherData(QueryData<S> queryData, Query<S> query) {
        return queryData;
    }
}
