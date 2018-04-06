package com.danil.etl.dao;

import com.danil.etl.entity.AenaflightTmp;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Repository
public abstract class AbstractObjectDao<O> {
    @PersistenceContext
    protected EntityManager entityManager;

    public void save(O aenaflightTmp) {
        entityManager.persist(aenaflightTmp);
    }
}
