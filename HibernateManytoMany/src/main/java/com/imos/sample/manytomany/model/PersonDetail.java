/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.manytomany.model;

import com.imos.sample.base.model.Base;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "PERSON_DETAIL")
public class PersonDetail extends Base {
    
    private static final long serialVersionUID = 7185780045297007806L;
    
    @OneToOne
    @JoinColumn(name = "PERSON_ID_FK")
    private Person person;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    
    @Column(name = "EMAIL")
    private String email;
}
