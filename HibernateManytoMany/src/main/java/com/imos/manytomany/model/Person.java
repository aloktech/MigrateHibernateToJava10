/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.manytomany.model;

import com.imos.base.model.Base;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ameher
 */
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"personDetail", "address"})
@Entity
@Table(name = "PERSON")
public class Person extends Base {

    private static final long serialVersionUID = 4152831022017778012L;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SALT_VALUE")
    private String saltValue;

    @OneToOne
    @JoinColumn(name = "PERSON_DETAIL_ID_FK")
    private PersonDetail personDetail;

    @OneToMany
    @JoinTable(name = "PERSON_ADDRESS", joinColumns = {
        @JoinColumn(name = "PERSON_ID_FK")}, inverseJoinColumns = {
        @JoinColumn(name = "ADDRESS_ID_FK")})
    private List<PersonAddress> addresses = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "PERSON_EVENT", joinColumns = {
        @JoinColumn(name = "PERSON_ID_FK")}, inverseJoinColumns = {
        @JoinColumn(name = "EVENT_ID_FK")})
    private List<Event> events = new ArrayList<>();
}
