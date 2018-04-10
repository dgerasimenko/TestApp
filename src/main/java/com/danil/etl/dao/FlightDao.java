package com.danil.etl.dao;

import com.danil.etl.entity.Flight;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class FlightDao extends AbstractObjectDao<Flight> {

    public void deleteByIds(List<Long> id) {
        super.deleteByIds(Flight.class, id);
    }

    public void deleteById(Long id) {
        super.deleteByIds(Flight.class, Arrays.asList(id));
    }

    public void deleteAll() {
        final String sql = "TRUNCATE TABLE aenaflight_2017_01;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    public List<Flight> getNextChunk(Long prevRecordId, int limit) {
        final Query hql = entityManager
                .createQuery("FROM Flight f WHERE f.id > :prevRecordId ORDER BY f.id")
                .setParameter("prevRecordId", prevRecordId);
        hql.setMaxResults(limit);
        List<Flight> list = hql.getResultList();
        return list;
    }

    public Class<Flight> getWorkingEntityClass() {
        return Flight.class;
    }
}
