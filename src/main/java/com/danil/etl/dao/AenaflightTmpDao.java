package com.danil.etl.dao;

import com.danil.etl.entity.AenaflightTmp;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AenaflightTmpDao extends AbstractObjectDao<AenaflightTmp> {

    public void save(AenaflightTmp aenaflightTmp) {
        super.save(aenaflightTmp);
    }
}
