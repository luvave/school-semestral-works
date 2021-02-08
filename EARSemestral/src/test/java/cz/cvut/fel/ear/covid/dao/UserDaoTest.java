package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.CovidApplication;
import cz.cvut.fel.ear.covid.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = CovidApplication.class)
public class UserDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserDao sut;

    public void prepareTestData() {
        User user = new User();
        user.setUsername("Andysek123");
        user.setFirstName("Andy");
        user.setLastName("Svancarova");
        user.setPassword("sdsfdfsddsf");

        em.persistAndFlush(user);
    }

    @Test
    public void findUserByUsername(){
        prepareTestData();

        User user = sut.findByUsername("Andysek123");

        Assert.assertEquals("Andysek123", user.getUsername());
    }

    @Test(expected = NoResultException.class)
    public void cannotFindNonexistentUser(){

        User user = sut.findByUsername("Pan Kostov");

    }

}
