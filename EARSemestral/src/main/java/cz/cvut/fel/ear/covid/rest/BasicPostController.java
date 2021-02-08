package cz.cvut.fel.ear.covid.rest;

import cz.cvut.fel.ear.covid.model.*;
import cz.cvut.fel.ear.covid.security.model.AuthenticationToken;
import cz.cvut.fel.ear.covid.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BasicPostController {

    private final BasicPostService basicPostService;
    private final SymptomService symptomService;
    private final TreatmentService treatmentService;
    private final TestingCenterService testingCenterService;

    private final TagService tagService;

    @Autowired
    public BasicPostController(BasicPostService basicPostService, SymptomService symptomService, TreatmentService treatmentService, TestingCenterService testingCenterService, TagService tagService) {
        this.basicPostService = basicPostService;
        this.symptomService = symptomService;
        this.treatmentService = treatmentService;
        this.testingCenterService = testingCenterService;
        this.tagService = tagService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BasicPost> getAll() {
        return basicPostService.findAll();
    }

    @GetMapping(value = "/symptoms", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Symptom> getAllSymptoms() {
        return symptomService.findAll();
    }
    @GetMapping(value = "/centers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TestingCenter> getAllCenters() {
        return testingCenterService.findAll();
    }
    @GetMapping(value = "/treatment", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Treatment> getAllTreatment() { return treatmentService.findAll(); }

    @GetMapping(value = "/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BasicPost> getAllByTagID(@PathVariable Integer id) { return basicPostService.findByTagId(id); }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping(value = "/symptoms/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Symptom> createSymptom(Principal principal, @RequestBody Symptom symptom){
        final AuthenticationToken auth = (AuthenticationToken) principal;
        symptom.setAuthor(auth.getPrincipal().getUser());
        symptomService.persist(symptom);
        return new ResponseEntity<>(symptom, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping(value = "/centers/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TestingCenter> createCenter(Principal principal, @RequestBody TestingCenter testingCenter){
        final AuthenticationToken auth = (AuthenticationToken) principal;
        testingCenter.setAuthor(auth.getPrincipal().getUser());
        testingCenterService.persist(testingCenter);
        return new ResponseEntity<>(testingCenter, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping(value = "/treatment/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Treatment> createTreatment(Principal principal, @RequestBody Treatment treatment){
        final AuthenticationToken auth = (AuthenticationToken) principal;
        treatment.setAuthor(auth.getPrincipal().getUser());
        treatmentService.persist(treatment);
        return new ResponseEntity<>(treatment, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@PathVariable Integer id){
        final BasicPost post = basicPostService.find(id);
        if (post == null) {
            return;
        }
        basicPostService.delete(post);
    }

}
