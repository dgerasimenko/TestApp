package com.danil.etl.dao;

import com.danil.etl.entity.Aenaflight;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class AenaflightDao extends AbstractObjectDao<Aenaflight> {

    public List<Aenaflight> getNextChunk(Long prevRecordId, int limit) {
        final Query hql = entityManager
                .createQuery("FROM Aenaflight a WHERE a.id > :prevRecordId ORDER BY a.id")
                .setParameter("prevRecordId", prevRecordId);
        hql.setMaxResults(limit);
        List<Aenaflight> list = hql.getResultList();
        return list;
    }
    public void deleteAll() {
        final String sql = "TRUNCATE TABLE aenaflight_2017_01;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
