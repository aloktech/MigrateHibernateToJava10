/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base;

import java.util.function.BiFunction;
import lombok.Getter;
import org.hibernate.query.Query;

/**
 *
 * @author Pintu
 */
public enum QueryResultType {
    
    LIST(QueryDataType::getListData),
    SINGLE(QueryDataType::getSingleData),
    OTHER(QueryDataType::getOtherData);

    @Getter
    private final BiFunction<QueryData, Query, QueryData> dataType;

    private QueryResultType(BiFunction<QueryData, Query, QueryData> dataType) {
        this.dataType = dataType;
    }

}
