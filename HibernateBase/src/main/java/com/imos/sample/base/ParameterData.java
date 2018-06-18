/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ameher
 */
@Getter
@Setter
public class ParameterData implements Serializable {

    private final String name;
    private final Object value;

    public ParameterData(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
