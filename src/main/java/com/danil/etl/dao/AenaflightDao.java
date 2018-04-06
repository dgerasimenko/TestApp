package com.danil.etl.dao;

import com.danil.etl.entity.Aenaflight;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class AenaflightDao extends AbstractObjectDao<Aenaflight> {

    @Transactional
    public List<Aenaflight> getNextChunk(Long prevRecordId, int limit) {
        final Query hql = entityManager
                .createQuery( "FROM Aenaflight WHERE id > :prevRecordId")
                .setParameter("prevRecordId", prevRecordId);
        hql.setMaxResults(limit);
        List<Aenaflight> list = hql.getResultList();
        return list;
    }
}
