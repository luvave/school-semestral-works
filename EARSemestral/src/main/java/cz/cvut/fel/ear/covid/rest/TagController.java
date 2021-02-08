package cz.cvut.fel.ear.covid.rest;

import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Tag;
import cz.cvut.fel.ear.covid.service.BasicPostService;
import cz.cvut.fel.ear.covid.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    private final BasicPostService basicPostService;

    @Autowired
    public TagController(TagService tagService, BasicPostService basicPostService) {
        this.tagService = tagService;
        this.basicPostService = basicPostService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag){
        tagService.persist(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tag> getTags() {
        return tagService.findAll();
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> getById(@PathVariable Integer id) {
        final Tag tag = tagService.find(id);
        if (tag == null) {
            return new ResponseEntity<>(tag, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BasicPost>> getPostByTag(@PathVariable Integer id) {
        final Tag tag = tagService.find(id);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(basicPostService.findByTagId(tag.getId()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{id}/add/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> addPostToCategory(@PathVariable Integer id, @PathVariable Integer postId) {
        Tag tag = tagService.find(id);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        BasicPost post = basicPostService.find(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        tagService.addTagToPost(tag, post);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{tagId}/remove/{postId}")
    public ResponseEntity<Tag> removeTagFromPost(@PathVariable Integer tagId, @PathVariable Integer postId) {
        Tag tag = tagService.find(tagId);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        BasicPost post = basicPostService.find(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        tagService.removeTagFromPost(tag, post);
            return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@PathVariable Integer id){
        final Tag post = tagService.find(id);
        if (post == null) {
            return;
        }
        tagService.delete(post);
    }
}
