package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Objects;

@Repository
public class UserDao extends BaseDao<User>{

    public UserDao() {
        super(User.class);
    }

    public User findByUsername(String username) {
        Objects.requireNonNull(username);
        try {
            return em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException("No user with this username");
        }
    }
}
