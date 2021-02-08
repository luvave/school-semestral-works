package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.model.Tool;
import cz.cvut.fel.ear.covid.model.Treatment;
import org.springframework.stereotype.Repository;

@Repository
public class TreatmentDao extends BaseDao<Treatment>{

    protected TreatmentDao() {
        super(Treatment.class);
    }
}