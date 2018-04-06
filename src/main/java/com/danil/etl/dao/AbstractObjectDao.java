package com.danil.etl.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional
public abstract class AbstractObjectDao<O> {
    @PersistenceContext
    protected EntityManager entityManager;

    public void save(O aenaflightTmp) {
        entityManager.persist(aenaflightTmp);
    }

    protected List<O> getAll(Class<O> clazz) {
        final Query query = entityManager.createQuery("FROM " + clazz.getSimpleName());
        return query.getResultList();
    }
}
