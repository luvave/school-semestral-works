package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.CovidApplication;
import cz.cvut.fel.ear.covid.model.Symptom;
import cz.cvut.fel.ear.covid.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackageClasses = CovidApplication.class)
public class SymptomServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SymptomService sut;

    @Autowired
    private UserService userService;

    @Test
   // @Rollback(false)
    public void addSymptomPost(){
        User u = new User();
        u.setPassword("test");
        u.setUsername("testovaciuser");
        u.setFirstName("Pepa");
        u.setLastName("Novak");
        userService.newAuthor(u);
        Symptom s = new Symptom();
        s.setDate(new Date());
        s.setAuthor(userService.getByUsername("testovaciuser"));
        s.setPercentage(20);
        s.setShortDescription("blbalklaskfasldkas");
        s.setTitle("Testovaci symptom");
        sut.persist(s);
    }
}
