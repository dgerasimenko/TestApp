package com.danil.etl.dao;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public abstract class AbstractObjectDao<O> implements IBaseObjectDao<O> {
    @PersistenceContext
    protected HibernateEntityManager entityManager;

    private Session session;

    /**
     * Returns object by an ID.
     *
     * @param <O>       the domain object type
     * @param id        the object id
     * @param className the object class
     * @return the domain object or null
     */
    @SuppressWarnings("unchecked")
    protected <O extends Object> O findById(final Class<O> className, final Long id) {
        final O result = (O) entityManager.getSession().get(className, id);
        if (result == null) {
            throw new ObjectNotFoundException(id, className.getName());
        }
        return result;
    }

    /**
     * Deletes the object by the ID.
     *
     * @param id        the object id
     * @param className the object class
     */
    @SuppressWarnings("unchecked")
    protected void delete(final Class<O> className, final Long id) {
        O object = (O) entityManager.getSession().get(className, id);
        delete(object);
    }

    public void delete(O object) {
        entityManager.getSession().delete(object);
    }

    /**
     * Saves the object.
     *
     * @param object the object to save
     */
    public void saveOrUpdate(O object) {
        entityManager.getSession().saveOrUpdate(object);
    }

    /**
     * Returns all object for the specified class.
     * @param clazz
     * @return all object for the specified class.
     */
    protected List<O> loadObjects(Class<O> clazz) {
        @SuppressWarnings("unchecked")
        final List<O> objectList = entityManager.getSession()
                .createCriteria(clazz).list();
        return objectList;
    }



    protected List<O> loadObjectsPage(Class<O> clazz, int offset, int limit) {
        @SuppressWarnings("unchecked")
        final List<O> objectList = entityManager.getSession()
                .createCriteria(clazz)
                .setFirstResult(offset)
                .setMaxResults(limit).list();
        return objectList;
    }
}
