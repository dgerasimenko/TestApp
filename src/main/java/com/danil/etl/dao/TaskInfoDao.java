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

    public List<TaskInfo> getOrderedByStart(Class taskType) {
        final Query query = entityManager.createQuery("FROM TaskInfo t WHERE t.taskType=:taskType ORDER BY t.startIndex ASC")
                .setParameter("taskType", taskType.getSimpleName());
        return query.getResultList();
    }

    public void deleteAll() {
        final String sql = "TRUNCATE TABLE task_info;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    public void deleteById(Long id) {
        super.deleteByIds(TaskInfo.class, Arrays.asList(id));
    }
}
