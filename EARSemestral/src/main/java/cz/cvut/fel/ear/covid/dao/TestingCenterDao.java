package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.model.TestingCenter;
import org.springframework.stereotype.Repository;

@Repository
public class TestingCenterDao extends BaseDao<TestingCenter>{

    protected TestingCenterDao() {
        super(TestingCenter.class);
    }
}