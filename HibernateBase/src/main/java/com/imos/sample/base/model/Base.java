/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author ameher
 */
@Data
@MappedSuperclass
public class Base implements IData, Serializable {

    private static final long serialVersionUID = -3092591975254283805L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @Column(name = "ACTIVE")
    protected boolean active = true;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    protected Date createdDate = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETED_DATE")
    protected Date deletedDate;
}
