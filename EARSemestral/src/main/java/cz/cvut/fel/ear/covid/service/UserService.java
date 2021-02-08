package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.dao.UserDao;
import cz.cvut.fel.ear.covid.model.Role;
import cz.cvut.fel.ear.covid.model.Treatment;
import cz.cvut.fel.ear.covid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    private final UserDao dao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void newAuthor(User user) {
        Objects.requireNonNull(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.EDITOR);
        }
        dao.persist(user);
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return dao.findByUsername(username) != null;
    }

    @Transactional
    public User getByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Transactional
    public void delete(User user) { dao.remove(user);}
}
