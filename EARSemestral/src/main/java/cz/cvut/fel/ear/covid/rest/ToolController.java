package cz.cvut.fel.ear.covid.rest;

import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Tool;
import cz.cvut.fel.ear.covid.service.BasicPostService;
import cz.cvut.fel.ear.covid.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    private final ToolService toolService;

    @Autowired
    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tool> getAll() {
        return toolService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> getDataFromToolID(@PathVariable Integer id) {
        Tool tool = toolService.find(id);
        if(tool == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return tool.getToolImplementation().work();
    }
}
