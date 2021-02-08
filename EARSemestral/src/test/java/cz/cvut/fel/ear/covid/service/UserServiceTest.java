package cz.cvut.fel.ear.covid.service;

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

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = CovidApplication.class)
public class UserServiceTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserService sut;

    public User prepareTestData() {
        User user = new User();
        user.setUsername("Andysek123");
        user.setFirstName("Andy");
        user.setLastName("Svancarova");
        user.setPassword("sdsfdfsddsf");

        return user;
    }

    @Test
    public void createNewAuthor(){
        User user = prepareTestData();

        sut.newAuthor(user);

        Assert.assertEquals(user.getUsername(), sut.getByUsername(user.getUsername()).getUsername());
    }
}
