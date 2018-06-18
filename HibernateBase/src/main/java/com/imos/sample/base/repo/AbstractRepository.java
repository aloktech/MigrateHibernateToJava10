/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base.repo;

import com.imos.sample.base.DBOperationType;
import com.imos.sample.base.HibernateService;
import com.imos.sample.base.ParameterData;
import com.imos.sample.base.QueryData;
import com.imos.sample.base.QueryResultType;
import com.imos.sample.base.QueryType;
import com.imos.sample.base.model.IData;
import com.imos.sample.base.function.BiConsumerWithException;
import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.base.exception.DuplicateEntryException;
import com.imos.sample.base.function.FunctionWithException;
import com.imos.sample.base.exception.InvalidEntryException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author ameher
 * @param <T>
 */
@Log4j2
public class AbstractRepository<T extends IData> implements IRepository<T> {

    private static final ExecutorService SERVICE = Executors.newCachedThreadPool();

    private static final HibernateService HIBERNATE_SERVICE = HibernateService.INSTANCE;

    private final ReentrantLock reLock = new ReentrantLock();

    private final List<ParameterData> parameterData = new ArrayList<>();

    private final BiConsumerWithException<Session, T> save = (s, d) -> s.save(d);

    private final BiConsumerWithException<Session, T> update = (s, d) -> s.update(d);

    private final BiConsumerWithException<Session, T> delete = (s, d) -> s.delete(d);

    protected boolean cacheable;

    @Override
    public void addData(T data) throws RepositoryException {
        checkNull(data);
        executeDBOperation(Arrays.asList(data), save, DBOperationType.SAVED);
    }

    @Override
    public void addData(T... data) throws RepositoryException {
        checkDataValidity(Arrays.asList(data));
        executeDBOperation(Arrays.asList(data), save, DBOperationType.SAVED);
    }

    @Override
    public void updateData(T data) throws RepositoryException {
        checkNull(data);
        executeDBOperation(Arrays.asList(data), update, DBOperationType.UPDATED);
    }

    @Override
    public void updateData(T... data) throws RepositoryException {
        checkDataValidity(Arrays.asList(data));
        executeDBOperation(Arrays.asList(data), update, DBOperationType.UPDATED);
    }

    @Override
    public void deleteData(T data) throws RepositoryException {
        checkNull(data);
        executeDBOperation(Arrays.asList(data), delete, DBOperationType.DELETED);
    }

    @Override
    public void deleteData(T... data) throws RepositoryException {
        checkDataValidity(Arrays.asList(data));
        executeDBOperation(Arrays.asList(data), delete, DBOperationType.DELETED);
    }

    @Override
    public T getById(Class<T> cls, long id) throws RepositoryException {
        Future<T> future = SERVICE.submit(() -> {
            reLock.lock();
            T data = null;
            Session session = HIBERNATE_SERVICE.openConnection();
            try {
                session.beginTransaction();
                data = session.find(cls, id);
                session.getTransaction().commit();
                log.info("{} is {} {} database", cls.getSimpleName(), "from", DBOperationType.QUERIED);
            } catch (Exception ex) {
                log.error("{} failed {} {} database: {}", cls.getSimpleName(),
                        "from", DBOperationType.QUERIED, ex.getMessage());
                HIBERNATE_SERVICE.transactionRollBack(session);
                if (ex instanceof RepositoryException) {
                    throw ex;
                } else {
                    throw new RepositoryException(ex);
                }
            } finally {
                HIBERNATE_SERVICE.closeConnection(session);
                reLock.unlock();
            }
            return data;
        });
        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RepositoryException(e);
        }
    }

    public void executeDBOperation(List<T> data, BiConsumerWithException<Session, T> consumer, DBOperationType op) throws RepositoryException {
        Future<Void> future = SERVICE.submit(() -> {
            reLock.lock();
            Session session = HIBERNATE_SERVICE.openConnection();
            try {
                session.beginTransaction();
                for (T d : data) {
                    consumer.apply(session, d);
                    log.info("{} is {} {} database", d.getClass().getSimpleName(),
                            op == DBOperationType.DELETED ? "from" : "to", op);
                }
                session.getTransaction().commit();
            } catch (Exception ex) {
                log.error("{} failed {} {} database: {}", data.get(0).getClass().getSimpleName(),
                        op == DBOperationType.DELETED ? "from" : "to", op, ex.getMessage());
                HIBERNATE_SERVICE.transactionRollBack(session);
                Throwable th = ex.getCause();
                if (Objects.nonNull(th) && th instanceof SQLIntegrityConstraintViolationException
                        && th.getMessage().contains("Duplicate")) {
                    String msg = th.getMessage();
                    msg = msg.substring(0, msg.indexOf("for"));
                    throw new DuplicateEntryException(msg, ex);
                } else if (ex instanceof RepositoryException) {
                    throw ex;
                } else {
                    throw new RepositoryException(ex);
                }
            } finally {
                HIBERNATE_SERVICE.closeConnection(session);
                reLock.unlock();
            }
            return null;
        });
        try {
            future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RepositoryException(e);
        }
    }

    protected <S> Optional<S> extractUniqueResult(String queryStr) throws RepositoryException {
        return extractResult(queryStr, QueryResultType.SINGLE, QueryType.JPA_QUERY).getSingleData();
    }

    protected <S> Optional<S> extractUniqueResult(String queryStr, Class<S> cls) throws RepositoryException {
        return extractResultOfType(queryStr, QueryResultType.SINGLE, QueryType.JPA_QUERY, cls).getSingleData();
    }

    protected <S> Optional<S> extractUniqueResult(String queryStr, QueryType queryType) throws RepositoryException {
        return extractResult(queryStr, QueryResultType.SINGLE, queryType).getSingleData();
    }

    protected <S> Optional<S> extractUniqueResult(String queryStr, QueryType queryType, Class<S> cls) throws RepositoryException {
        return extractResultOfType(queryStr, QueryResultType.SINGLE, queryType, cls).getSingleData();
    }

    protected <S> List<S> extractListAsResult(String queryStr) throws RepositoryException {
        return extractResult(queryStr, QueryResultType.LIST, QueryType.JPA_QUERY).getListOfData();
    }

    protected <S> List<S> extractListAsResult(String queryStr, Class<S> cls) throws RepositoryException {
        return extractResultOfType(queryStr, QueryResultType.LIST, QueryType.JPA_QUERY, cls).getListOfData();
    }

    protected <S> List<S> extractListAsResult(String queryStr, QueryType queryType, Class<S> cls) throws RepositoryException {
        return extractResultOfType(queryStr, QueryResultType.LIST, queryType, cls).getListOfData();
    }

    protected <S> List<S> extractListAsResult(String queryStr, QueryType queryType, Function<Object, S> convert) throws RepositoryException {
        return (List<S>) extractResult(queryStr, QueryResultType.LIST, queryType)
                .getListOfData()
                .stream()
                .map(convert)
                .collect(Collectors.toList());
    }

    private <S> QueryData extractResult(String queryStr, QueryResultType queryResultType, QueryType queryType) throws RepositoryException {
        return extractResultOfType(queryStr, queryResultType, queryType, null);
    }

    private <S> QueryData<S> extractResultOfType(String queryStr, QueryResultType queryResultType, QueryType queryType, Class<S> cls) throws RepositoryException {
        QueryData<S> queryData = new QueryData<>();
        try {
            FunctionWithException<Session, QueryData> con = s -> {
                Query query = queryType.getDataType().apply(s, queryStr, cls);
                return executeQuery(query, queryResultType, new QueryData<>());
            };
            queryData = executeSession(con, cls);
        } catch (RepositoryException ex) {
            throw ex;
        }
        return queryData;
    }

    private <S> QueryData executeSession(FunctionWithException<Session, QueryData> con, Class<S> cls) throws RepositoryException {
        Future<QueryData> future = SERVICE.submit(() -> {
            QueryData output = null;
            reLock.lock();
            Session session = HIBERNATE_SERVICE.openConnection();
            try {
                session.beginTransaction();
                output = con.apply(session);
                session.getTransaction().commit();
                log.info("{} is {} from database", cls.getSimpleName(), DBOperationType.QUERIED);
            } catch (Exception ex) {
                log.error("Failed to {} {} from database: {}", DBOperationType.QUERIED, cls.getSimpleName(), ex.getMessage());
                HIBERNATE_SERVICE.transactionRollBack(session);
                throw new RepositoryException(ex);
            } finally {
                HIBERNATE_SERVICE.closeConnection(session);
                parameterData.clear();
                reLock.unlock();
            }
            return output;
        });
        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RepositoryException(e);
        }
    }

    private <S> QueryData<S> executeQuery(Query<S> query, QueryResultType queryResultType, QueryData<S> queryData) {
        parameterData.forEach(d -> query.setParameter(d.getName(), d.getValue()));
        query.setHint("org.hibernate.cacheable", cacheable);
        return queryResultType.getDataType().apply(queryData, query);
    }

    protected void addParameter(String name, Object data) {
        parameterData.add(new ParameterData(name, data));
    }

    private void checkDataValidity(List<T> data) throws InvalidEntryException {
        for (T d : data) {
            checkNull(d);
        }
    }

    private void checkNull(T data) throws InvalidEntryException {
        if (Objects.isNull(data)) {
            throw new InvalidEntryException("Data is null/empty");
        }
    }
}
