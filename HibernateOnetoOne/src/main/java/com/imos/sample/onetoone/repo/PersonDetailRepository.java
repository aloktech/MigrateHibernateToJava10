/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.onetoone.repo;

import com.imos.sample.base.repo.AbstractRepository;
import com.imos.sample.base.QueryType;
import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.onetoone.model.PersonDetail;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author ameher
 */
@Log4j2
public class PersonDetailRepository extends AbstractRepository {

    public List<PersonDetail> findAllPersonDetail() throws RepositoryException {
        List<PersonDetail> persons = new ArrayList<>();
        try {
            persons = extractListAsResult("from PersonDetail p", QueryType.JPA_QUERY, PersonDetail.class);
            log.info("Find all Person Detail");
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        return persons;
    }
}
