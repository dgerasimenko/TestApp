package com.danil.etl.dao;

import java.util.List;

public interface IBaseObjectDao<O> {

    /**
     * Returns object by an ID.
     *
     * @param id        the object id
     * @return the domain object or null
     */
    O getObject(Long id);

    /**
     * Returns all object for the specified class.
     * @return all object for the specified class.
     */
    List<O> getAllObjects();

    /**
     * Returns objects page
     * @return objects page.
     */
    List<O> getObjectsPage(int offset, int limit);

    /**
     * Deletes the object.
     *
     * @param object the object to delete
     */
    void delete(O object);

    /**
     * Deletes the object by the ID.
     *
     * @param id        the object id
     */
    void delete(Long id);

    /**
     * Saves the object.
     *
     * @param object the object to save
     */
    void saveOrUpdate(O object);
}
