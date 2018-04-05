package com.danil.etl.dao;

import com.danil.etl.entity.Aenaflight;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AenaflightDao  extends AbstractObjectDao<Aenaflight> {

    @Override
    public Aenaflight getObject(Long id) {
        return findById(Aenaflight.class, id);
    }

    @Override
    public List<Aenaflight> getAllObjects() {
        return loadObjects(Aenaflight.class);
    }

    @Override
    public List<Aenaflight> getObjectsPage(int offset, int limit) {
        return loadObjectsPage(Aenaflight.class, offset, limit);
    }

    @Override
    public void delete(Long id) {
        delete(Aenaflight.class, id);
    }
}
