/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.onetoone.model.support;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author pintu
 */
@Data
@AllArgsConstructor
public class PersonInfo implements Serializable {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
}
