package cz.cvut.fel.ear.covid.rest;

import cz.cvut.fel.ear.covid.model.Article;
import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.service.ArticleService;
import cz.cvut.fel.ear.covid.service.BasicPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    private final BasicPostService basicPostService;

    @Autowired
    public ArticleController(ArticleService articleService, BasicPostService basicPostService) {
        this.articleService = articleService;
        this.basicPostService = basicPostService;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> getArticleFromPost(@PathVariable Integer id){
        Article ret = basicPostService.getArticle(basicPostService.find(id));
        if(ret==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(value = "/have/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap> haveArticleFromPost(@PathVariable Integer id){
        HashMap ret = new HashMap<String, String>();
        if(basicPostService.haveArticle(basicPostService.find(id))){
            ret.put("haveArticle","true");
        }
        else {
            ret.put("haveArticle","false");
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    /*@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> createArticle(@RequestBody Article article){
        articleService.persist(article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }*/

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping(value = "/create/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> createFromPostArticle(@PathVariable Integer id, @RequestBody Article article){
        BasicPost post = basicPostService.find(id);
        if(post == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        article.setAssociatedPost(post);
        articleService.persist(article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }
}
