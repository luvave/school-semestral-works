package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.CovidApplication;
import cz.cvut.fel.ear.covid.model.Article;
import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Symptom;
import cz.cvut.fel.ear.covid.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = CovidApplication.class)
public class BasicPostServiceTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BasicPostService sut;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    public BasicPost prepareTestData() {
        Article article = new Article();
        article.setTitle("Test Article");

        User author = new User();
        author.setId(992);
        author.setFirstName("Pepa");
        author.setLastName("Novak");
        author.setPassword("sfsf");
        author.setUsername("PepAn");

        Symptom basicPost = new Symptom();
        basicPost.setId(992);
        basicPost.setTitle("Test Basic Post");
        basicPost.setDate(new Date());
        basicPost.setAuthor(author);
        basicPost.setShortDescription("ssds");

        article.setAssociatedPost(basicPost);
        article.setId(995);

        em.persistAndFlush(author);
        em.persistAndFlush(basicPost);
        em.persistAndFlush(article);

        return basicPost;
    }

    @AfterEach
    public void deleteTestData(){
        em.clear();
    }

    @Test
    public void getArticleByPostId() {
        prepareTestData();

        Symptom sym = new Symptom();
        sym.setId(992);
        sym.setShortDescription("sdfsdf");

        Assert.assertEquals(995L, sut.getArticle(sym).getId().longValue());

    }

    @Test
    public void checkIfPostHasArticle(){
        BasicPost post = prepareTestData();

        Assert.assertTrue(sut.haveArticle(post));

    }

    @Test
    public void checkIfPostHasNoArticles(){
        User author = new User();
        author.setId(992);
        author.setFirstName("Pepa");
        author.setLastName("Novak");
        author.setPassword("sfsf");
        author.setUsername("PepAn");

        Symptom basicPost = new Symptom();
        basicPost.setId(992);
        basicPost.setTitle("Test Basic Post");
        basicPost.setDate(new Date());
        basicPost.setAuthor(author);
        basicPost.setShortDescription("ssds");

        Assert.assertFalse(sut.haveArticle(basicPost));
    }

}
