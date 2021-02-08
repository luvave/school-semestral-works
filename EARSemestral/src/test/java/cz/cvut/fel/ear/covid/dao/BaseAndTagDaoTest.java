package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.CovidApplication;
import cz.cvut.fel.ear.covid.model.Tag;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = CovidApplication.class)
public class BaseAndTagDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private TagDao sut;

    private Random rand;

    public void initiate() {
        rand = new Random();
    }

    @AfterEach
    public void deleteTestData(){
        em.clear();
    }


    @Test
    public void persistSavesSpecifiedInstanceFindRetrievesByIdentifier() {
        initiate();
        Tag tag = new Tag();
        tag.setName("TEST TAG");
        tag.setId(rand.nextInt(100));
        sut.persist(tag);
        assertNotNull(tag.getId());

        Tag result = em.find(Tag.class, tag.getId());
        assertNotNull(result);
        assertEquals(tag.getId(), result.getId());
        assertEquals(tag.getName(), result.getName());
    }

    @Test
    public void findAllRetrievesAllInstancesOfSpecifiedType() {
        initiate();
        Tag tag1 = new Tag();
        tag1.setName("TEST TAG1");
        tag1.setId(rand.nextInt(100));
        em.persistAndFlush(tag1);
        Tag tag2 = new Tag();
        tag2.setName("TEST TAG2");
        tag2.setId(rand.nextInt(100));
        em.persistAndFlush(tag2);

        final List<Tag> results = sut.findAll();
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(c -> c.getId().equals(tag1.getId())));
        assertTrue(results.stream().anyMatch(c -> c.getId().equals(tag2.getId())));
    }

    @Test
    public void updateUpdatesExistingInstanceOfObject() {
        initiate();
        Tag tag1 = new Tag();
        tag1.setName("TEST TAG1");
        tag1.setId(rand.nextInt(100));
        em.persistAndFlush(tag1);

        Tag update = new Tag();
        update.setId(tag1.getId());
        String newName = "New category name";
        update.setName(newName);
        sut.update(update);

        Tag result = sut.find(tag1.getId());
        assertNotNull(result);
        assertEquals(tag1.getName(), result.getName());
    }

    @Test
    public void removeRemovesSpecifiedInstanceOfObject() {
        initiate();
        Tag tag1 = new Tag();
        tag1.setName("TEST TAG1");
        tag1.setId(rand.nextInt(100));
        em.persistAndFlush(tag1);
        assertNotNull(em.find(Tag.class, tag1.getId()));
        em.detach(tag1);

        sut.remove(tag1);
        assertNull(em.find(Tag.class, tag1.getId()));
    }

    @Test(expected = PersistenceException.class)
    public void exceptionOnWronglyCalledPersistExpectedPersistenceException() {
        initiate();
        Tag tag1 = new Tag();
        tag1.setName("TEST TAG1");
        tag1.setId(rand.nextInt(100));
        em.persistAndFlush(tag1);
        em.remove(tag1);
        sut.update(tag1);
    }

}
