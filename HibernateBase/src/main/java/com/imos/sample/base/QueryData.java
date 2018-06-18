/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Pintu
 * @param <S>
 */
@Getter
@Setter
public class QueryData<S> {

    private Class<S> cls;
    private List<S> listOfData = new ArrayList<>();
    private Optional<S> singleData = Optional.empty();
}
