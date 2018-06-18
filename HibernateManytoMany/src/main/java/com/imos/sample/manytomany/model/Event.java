/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.manytomany.model;

import com.imos.sample.base.model.Base;
import com.imos.sample.manytomany.util.EventType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ameher
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "EVENT")
public class Event extends Base {

    private static final long serialVersionUID = -6309630345675610571L;

    @Column(name = "DATE_OF_EVENT")
    private LocalDate dateOfEvent;
    
    @Column(name = "START_TIME")
    private LocalDateTime startTime;
    
    @Column(name = "TIME_DURATION")
    private Duration timeDuration;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "EVENT_TYPE")
    private EventType eventType;

    @ManyToMany
    @JoinTable(name = "EVENT_HOST", joinColumns = {
        @JoinColumn(name = "EVENT_ID_FK")}, inverseJoinColumns = {
        @JoinColumn(name = "PERSON_ID_FK")})
    private List<Person> hosts = new ArrayList<>();

    @ManyToMany(mappedBy = "events")
    private List<Person> attendent = new ArrayList<>();
}
