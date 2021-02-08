package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.CovidApplication;
import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Symptom;
import cz.cvut.fel.ear.covid.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = CovidApplication.class)
public class BasicPostDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private BasicPostDao sut;

    public User prepareTestUserData() {
        User user = new User();
        user.setUsername("Andysek123");
        user.setFirstName("Andy");
        user.setLastName("Svancarova");
        user.setPassword("sdsfdfsddsf");
        em.persistAndFlush(user);

        return user;
    }

    public Symptom prepareTestSymptomData(User user){
        Symptom symptom = new Symptom();
        symptom.setShortDescription("sdfsd");
        symptom.setAuthor(user);
        symptom.setDate(new Date());
        symptom.setTitle("Sdfsdffdsdsf");

        return em.persistAndFlush(symptom);
    }

    @Test
    public void findAllPostsByAuthor(){
        User user =  prepareTestUserData();
        Symptom sym1 = prepareTestSymptomData(user);
        Symptom sym2 = prepareTestSymptomData(user);

        List<BasicPost> posts = sut.findAllByAuthor(user);

        Assert.assertEquals(2, posts.size());
        Assert.assertTrue(posts.contains(sym1));
        Assert.assertTrue(posts.contains(sym2));

    }


}
