/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.manytomany.model.support;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author ameher
 */
@Data
@AllArgsConstructor
public class PersonInfo implements Serializable {

    private static final long serialVersionUID = -8521178507243253921L;

    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String mobileNumber;
    private String email;
}
