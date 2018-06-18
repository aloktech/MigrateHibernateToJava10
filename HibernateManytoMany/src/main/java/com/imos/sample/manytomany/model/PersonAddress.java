/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.manytomany.model;

import com.imos.sample.base.model.Base;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author ameher
 */
@Data
@EqualsAndHashCode(callSuper = false, exclude = "person")
@ToString(exclude = "person")
@Entity
@Table(name = "ADDRESS")
public class PersonAddress extends Base {
    
    private static final long serialVersionUID = -5501900584938607668L;
 
    @ManyToOne
    @JoinColumn(name = "PERSON_ID_FK")
    private Person person;
    
    @Column(name = "FIRST_STREET")
    private String firstStreet;
    
    @Column(name = "SECOND_STREET")
    private String secondStreet;
    
    @Column(name = "PIN_CODE")
    private String pinCode;
    
    @Column(name = "CITY")
    private String city;
    
    @Column(name = "DISTRICT")
    private String district;
    
    @Column(name = "COUNTRY_STATE")
    private String countryState;
    
    @Column(name = "COUNTRY")
    private String country;
}
