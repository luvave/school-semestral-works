package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.CovidApplication;
import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Tag;
import cz.cvut.fel.ear.covid.model.Treatment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = CovidApplication.class)
public class TagServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TagService sut;

    @Test
    public void addTagToPostAddsTagToTargetPost() {
        Tag tag = new Tag();
        tag.setName("test tag");
        tag.setId(666);
        Treatment randomPost = new Treatment();
        randomPost.setTitle("Random post test");
        randomPost.setId(333);
        em.persist(randomPost);
        sut.persist(tag);
        sut.addTagToPost(tag, randomPost);

        BasicPost result = em.find(BasicPost.class, randomPost.getId());
        assertTrue(result.getTags().stream().anyMatch(c -> c.getId().equals(tag.getId())));
    }
}
