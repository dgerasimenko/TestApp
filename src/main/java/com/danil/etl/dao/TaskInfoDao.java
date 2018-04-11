package com.danil.etl.dao;

import com.danil.etl.entity.TaskInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class TaskInfoDao extends AbstractObjectDao<TaskInfo> {

    public List<TaskInfo> getOrderedByStart() {
        final Query query = entityManager.createQuery("FROM TaskInfo t ORDER BY t.startIndex ASC");
        return query.getResultList();
    }

    public void deleteById(Long id) {
        super.deleteByIds(TaskInfo.class, Arrays.asList(id));
    }
}
