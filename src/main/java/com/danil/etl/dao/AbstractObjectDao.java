package com.danil.etl.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;


@Repository
@Transactional
public abstract class AbstractObjectDao<O> {
    @PersistenceContext
    protected EntityManager entityManager;

    @Value("${batch.size}")
    private int batchSize;

    public void persist(O aenaflightTmp) {
        entityManager.persist(aenaflightTmp);
    }

    public O merge(O aenaflightTmp) {
        return entityManager.merge(aenaflightTmp);
    }


    public void mergeAll(Collection<O> entities) {
        int i = 0;
        for (O entity : entities) {
            entityManager.merge(entity);
            i++;
        }
        if (i % batchSize == 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

    public void persistAll(Collection<O> entities) {
        int i = 0;
        for (O entity : entities) {
            entityManager.merge(entity);
            i++;
        }
        if (i % batchSize == 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

    protected List<O> getAll(Class<O> clazz) {
        final Query query = entityManager.createQuery("FROM " + clazz.getSimpleName());
        return query.getResultList();
    }

    protected void deleteByIds(Class<O> clazz, List<Long> ids) {
        final Query query = entityManager.createQuery("DELETE FROM " + clazz.getSimpleName() + " o WHERE o.id IN (:ids)");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    protected void deleteAll(Class<O> clazz, List<O> objects) {
        for (O o : objects) {
            entityManager.remove(o);
        }
    }

    public void delete(O obj) {
        entityManager.remove(obj);
    }
}
