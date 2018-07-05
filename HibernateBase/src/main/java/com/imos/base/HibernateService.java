/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.base;

import com.imos.base.exception.RepositoryException;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author ameher
 */
@Log4j2
public enum HibernateService {

    INSTANCE;

    private String filePath;
    private SessionFactory sessionFactory;
    private BootstrapServiceRegistry registry;

    public void config() throws RepositoryException {
        config("");
    }

    public void config(String input) throws RepositoryException {
        this.filePath = input;
        log.info("Hibernate is opening");
        try {
            if (Objects.nonNull(registry) && Objects.nonNull(sessionFactory)) {
                log.info("StandardServiceRegistry is already generated");
                log.info("SessionFactory is already opened");
                return;
            }
            BootstrapServiceRegistryBuilder registryBuilder = new BootstrapServiceRegistryBuilder();
            registry = registryBuilder.build();
            log.info("StandardServiceRegistry is generated");
            Configuration config = new Configuration(registry);
            if (Objects.isNull(input) || input.isEmpty()) {
                log.info("Hibernate Configuration is from hibernate.cfg.xml");
                config = config.configure("hibernate.cfg.xml");
            } else if (input.endsWith("cfg.xml")) {
                log.info("Hibernate Configuration is from {}", input);
                config = config.configure(input);
            } else {
                throw new RepositoryException("Invalid File");
            }
            sessionFactory = config.buildSessionFactory();
            log.info("SessionFactory is opened");

            generateSqlScript();
        } catch (Exception e) {
            if (Objects.nonNull(registry)) {
                StandardServiceRegistryBuilder.destroy(registry.getParentServiceRegistry());
                log.info("StandardServiceRegistry is destroyed");
            }
            log.error("SessionFactory failed to opened: {}", e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private void generateSqlScript() {
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setDelimiter(";");
        schemaExport.setFormat(true);
        schemaExport.setManageNamespaces(true);
        schemaExport.setOutputFile("./ddl_ipdb.sql");
    }

    public Session openConnection() throws RepositoryException {
        Session session;
        if (Objects.isNull(sessionFactory)) {
            log.info("SessionFactory is not opened");
            config(Objects.isNull(this.filePath) ? "" : this.filePath);
        }
        session = sessionFactory.openSession();
        log.info("Session is opened");
        return session;
    }

    public void transactionRollBack(Session session) {
        if (Objects.nonNull(session) && session.getTransaction().isActive()) {
            session.getTransaction().rollback();
            log.info("Session Tranasction is rollback");
        }
    }

    public void closeConnection(Session session) {
        if (Objects.isNull(sessionFactory)) {
            log.info("SessionFactory is already closed");
        }
        if (Objects.nonNull(session) && session.isOpen()) {
            session.close();
            log.info("Session is closed");
        }
    }

    public void showStatistics() throws RepositoryException {
        Statistics stat = sessionFactory.getStatistics();
        if (Objects.nonNull(stat)) {
            log.info("ConnectCount : " + stat.getConnectCount());
            log.info("QueryExecutionCount : " + stat.getQueryExecutionCount());
            log.info("QueryExecutionMaxTime : " + stat.getQueryExecutionMaxTime());
            log.info("QueryCacheHitCount : " + stat.getQueryCacheHitCount());
            log.info("CloseStatementCount : " + stat.getCloseStatementCount());
            log.info("QueryExecutionMaxTimeQueryString : " + stat.getQueryExecutionMaxTimeQueryString());
            log.info("CollectionFetchCount : " + stat.getCollectionFetchCount());
        } else {
            log.error("Statistics is null");
        }
    }

    public void shutDown() throws RepositoryException {
        log.info("Hibernate is closing");
        if (Objects.nonNull(sessionFactory)) {
            showStatistics();
            sessionFactory.close();
            sessionFactory = null;
            log.info("SessionFactory is closed");
            if (Objects.nonNull(registry)) {
                StandardServiceRegistryBuilder.destroy(registry.getParentServiceRegistry());
                log.info("StandardServiceRegistry is destroyed");
            }
        } else {
            log.info("SessionFactory is already closed");
        }
    }
}
