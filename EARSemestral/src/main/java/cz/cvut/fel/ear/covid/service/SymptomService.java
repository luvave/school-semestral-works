package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.dao.BasicPostDao;
import cz.cvut.fel.ear.covid.dao.SymptomDao;
import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Symptom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SymptomService {

    private final BasicPostDao basicPostDao;

    private final SymptomDao symptomDao;

    @Autowired
    public SymptomService(BasicPostDao basicPostDao, SymptomDao symptomDao) {
        this.basicPostDao = basicPostDao;
        this.symptomDao = symptomDao;
    }

    @Transactional(readOnly = true)
    public List<Symptom> findAll() {
        return symptomDao.findAll();
    }

    @Transactional(readOnly = true)
    public Symptom find(Integer id) {
        return symptomDao.find(id);
    }

    @Transactional
    public void persist(Symptom post) {
        symptomDao.persist(post);
    }

    @Transactional
    public void update(Symptom post) {
        symptomDao.update(post);
    }

    @Transactional
    public void delete(Symptom post) { symptomDao.remove(post);}
}
