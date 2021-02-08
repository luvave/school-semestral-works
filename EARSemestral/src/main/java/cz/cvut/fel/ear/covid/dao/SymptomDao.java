package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.model.Article;
import cz.cvut.fel.ear.covid.model.Symptom;
import org.springframework.stereotype.Repository;

@Repository
public class SymptomDao extends BaseDao<Symptom>{

    protected SymptomDao() {
        super(Symptom.class);
    }
}
