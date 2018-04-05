package com.danil.etl.dao;

import com.danil.etl.entity.AenaflightTmp;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AenaflightTmpDao extends AbstractObjectDao<AenaflightTmp> {

    @Override
    public AenaflightTmp getObject(Long id) {
        return findById(AenaflightTmp.class, id);
    }

    @Override
    public List<AenaflightTmp> getAllObjects() {
        return loadObjects(AenaflightTmp.class);
    }

    @Override
    public List<AenaflightTmp> getObjectsPage(int offset, int limit) {
        return loadObjectsPage(AenaflightTmp.class, offset, limit);
    }

    @Override
    public void delete(Long id) {
        delete(AenaflightTmp.class, id);
    }
}
