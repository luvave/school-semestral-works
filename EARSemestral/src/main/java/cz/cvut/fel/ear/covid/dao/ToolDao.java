package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.model.TestingCenter;
import cz.cvut.fel.ear.covid.model.Tool;
import org.springframework.stereotype.Repository;

@Repository
public class ToolDao extends BaseDao<Tool>{

    protected ToolDao() {
        super(Tool.class);
    }
}