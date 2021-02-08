package cz.cvut.fel.ear.covid.service.tools;

import org.springframework.http.ResponseEntity;

import java.util.Map;

//THIS WILL BE IMPLEMENTED FOR EACH TOOL
public interface ToolImplementation {
    public ResponseEntity<Map> work();
}
