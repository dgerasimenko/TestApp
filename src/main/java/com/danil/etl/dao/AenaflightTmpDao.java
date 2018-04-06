package com.danil.etl.dao;

import com.danil.etl.entity.AenaflightTmp;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class AenaflightTmpDao extends AbstractObjectDao<AenaflightTmp> {

    public List<AenaflightTmp> getAll() {
        return super.getAll(AenaflightTmp.class);
    }

    public List<AenaflightTmp> getOrderedByStart() {
        final Query query = entityManager.createQuery("FROM AenaflightTmp a ORDER BY a.startIndex ASC");
        return query.getResultList();
    }

    public void deleteAll() {
        final String sql = "TRUNCATE TABLE aenaflight_tmp;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
