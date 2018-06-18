/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.manytomany.test;

import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.base.HibernateService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ameher
 */
public class SampleBaseTest {
    
    @BeforeAll
    public static void setUp() {
        try {
            HibernateService.INSTANCE.config("test.hibernate.cfg.xml");
        } catch (RepositoryException ex) {
            Logger.getLogger(SampleBaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void shutDown() {
        try {
            HibernateService.INSTANCE.shutDown();
        } catch (RepositoryException ex) {
            Logger.getLogger(SampleBaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void hibernateConnectionCheck() {
        try {
            Session session = HibernateService.INSTANCE.openConnection();
            HibernateService.INSTANCE.closeConnection(session);
        } catch (RepositoryException ex) {
            Logger.getLogger(SampleBaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
