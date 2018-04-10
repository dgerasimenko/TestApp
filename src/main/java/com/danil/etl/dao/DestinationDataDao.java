package com.danil.etl.dao;

import com.danil.etl.entity.DestinationData;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DestinationDataDao extends AbstractObjectDao<DestinationData> {
    public List<DestinationData> getAll() {
        return super.getAll(DestinationData.class);
    }

    @Override
    public void deleteAll() {

    }
}
