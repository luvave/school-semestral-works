package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.dao.TestingCenterDao;
import cz.cvut.fel.ear.covid.dao.ToolDao;
import cz.cvut.fel.ear.covid.model.TestingCenter;
import cz.cvut.fel.ear.covid.model.Tool;
import cz.cvut.fel.ear.covid.service.tools.InfectedTool;
import cz.cvut.fel.ear.covid.service.tools.ToolImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class ToolService {

    private final ToolDao toolDao;

    @Autowired
    public ToolService(ToolDao toolDao) {
        this.toolDao = toolDao;
    }

    public ToolImplementation getImpl(Tool tool){
        if(tool.getToolImplementation()==null){
            if(tool.getName().equals("infectedTool")){
                return new InfectedTool();
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Tool> findAll() {
        List<Tool> tools = toolDao.findAll();
        for(Tool t : tools){
            t.setToolImplementation(getImpl(t));
            if(t.getToolImplementation()==null){
                throw new NoResultException("Tool was not found in database->something wrong");
            }
        }
        return tools;
    }

    @Transactional(readOnly = true)
    public Tool find(Integer id) {
        Tool tool = toolDao.find(id);
        tool.setToolImplementation(getImpl(tool));
        if(tool.getToolImplementation()==null){
            throw new NoResultException("Tool was not found in database->something wrong");
        }
        return tool;
    }
}
