package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.dao.TestingCenterDao;
import cz.cvut.fel.ear.covid.dao.TreatmentDao;
import cz.cvut.fel.ear.covid.model.Symptom;
import cz.cvut.fel.ear.covid.model.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TreatmentService {

    private final TreatmentDao treatmentDao;

    @Autowired
    public TreatmentService(TreatmentDao treatmentDao) {
        this.treatmentDao = treatmentDao;
    }

    @Transactional(readOnly = true)
    public List<Treatment> findAll() {
        return treatmentDao.findAll();
    }

    @Transactional(readOnly = true)
    public Treatment find(Integer id) {
        return treatmentDao.find(id);
    }

    @Transactional
    public void persist(Treatment post) {
        treatmentDao.persist(post);
    }

    @Transactional
    public void update(Treatment post) {
        treatmentDao.update(post);
    }

    @Transactional
    public void delete(Treatment post) { treatmentDao.remove(post);}
}
