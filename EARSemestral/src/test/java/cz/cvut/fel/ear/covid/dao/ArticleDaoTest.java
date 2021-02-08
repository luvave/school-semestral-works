package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.CovidApplication;
import cz.cvut.fel.ear.covid.model.Article;
import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Symptom;
import cz.cvut.fel.ear.covid.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.assertj.AssertableWebApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = CovidApplication.class)
public class ArticleDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ArticleDao sut;

    public void prepareTestData(){
        Article article = new Article();
        article.setTitle("Test Article");

        User author = new User();
        author.setId(999);
        author.setFirstName("Pepa");
        author.setLastName("Novak");
        author.setPassword("sfsf");
        author.setUsername("PepAn");

        Symptom basicPost = new Symptom();
        basicPost.setId(998);
        basicPost.setTitle("Test Basic Post");
        basicPost.setDate(new Date());
        basicPost.setAuthor(author);
        basicPost.setShortDescription("ssds");

        article.setAssociatedPost(basicPost);

        em.persistAndFlush(author);
        em.persistAndFlush(basicPost);
        em.persistAndFlush(article);
    }

    @AfterEach
    public void deleteTestData(){
        em.clear();
    }

    @Test
    public void findArticleByPostId(){
        prepareTestData();

        Article ar = sut.findByAPostId(998);

        Assert.assertEquals("Test Article", ar.getTitle());
        Assert.assertEquals("Test Basic Post", ar.getAssociatedPost().getTitle());
    }

    @Test(expected = PersistenceException.class)
    public void cannotFindNonexistentArticle(){

        Article ar = sut.findByAPostId(6545654);
    }

}
