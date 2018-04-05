package com.danil.etl.dao;

import com.danil.etl.entity.AenaflightSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AenaflightSourceDao extends AbstractObjectDao<AenaflightSource> {

    @Override
    public AenaflightSource getObject(Long id) {
        return findById(AenaflightSource.class, id);
    }

    @Override
    public List<AenaflightSource> getAllObjects() {
        return loadObjects(AenaflightSource.class);
    }

    @Override
    public List<AenaflightSource> getObjectsPage(int offset, int limit) {
        return loadObjectsPage(AenaflightSource.class, offset, limit);
    }

    @Override
    public void delete(Long id) {
        delete(AenaflightSource.class, id);
    }
}
