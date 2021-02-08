package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.dao.SymptomDao;
import cz.cvut.fel.ear.covid.dao.TestingCenterDao;
import cz.cvut.fel.ear.covid.model.Symptom;
import cz.cvut.fel.ear.covid.model.TestingCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestingCenterService {
    private final TestingCenterDao testingCenterDao;

    @Autowired
    public TestingCenterService(TestingCenterDao testingCenterDao) {
        this.testingCenterDao = testingCenterDao;
    }

    @Transactional(readOnly = true)
    public List<TestingCenter> findAll() {
        return testingCenterDao.findAll();
    }

    @Transactional(readOnly = true)
    public TestingCenter find(Integer id) {
        return testingCenterDao.find(id);
    }

    @Transactional
    public void persist(TestingCenter post) {
        testingCenterDao.persist(post);
    }

    @Transactional
    public void update(TestingCenter post) {
        testingCenterDao.update(post);
    }

    @Transactional
    public void delete(TestingCenter post) { testingCenterDao.remove(post);}
}
