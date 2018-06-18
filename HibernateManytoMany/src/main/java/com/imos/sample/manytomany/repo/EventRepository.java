/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.manytomany.repo;

import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.base.repo.AbstractRepository;
import com.imos.sample.manytomany.model.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author ameher
 */
@Log4j2
public class EventRepository extends AbstractRepository {

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try {
            events = extractListAsResult("from Person p", Event.class);
        } catch (RepositoryException ex) {
            Logger.getLogger(PersonRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }
}
