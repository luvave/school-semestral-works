package cz.cvut.fel.ear.covid.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AUTHORS")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String username;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "author")
    private List<BasicPost> createdPosts;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "subscribers")
    @OrderBy("name")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Tool> favoriteTools;

    public List<Tool> getFavoriteTools() {
        return favoriteTools;
    }

    public void setFavoriteTools(List<Tool> favoriteTools) {
        this.favoriteTools = favoriteTools;
    }

    public User() {
        this.role = Role.EDITOR;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void erasePassword() {
        this.password = null;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public List<BasicPost> getCreatedPosts() {
        return createdPosts;
    }

    public void setCreatedPosts(List<BasicPost> createdPosts) {
        this.createdPosts = createdPosts;
    }

    public boolean addCreatedPost(BasicPost post){
        if(post==null) return false;
        if(this.createdPosts == null){
            this.createdPosts = new ArrayList<>();
        }
        createdPosts.add(post);
        return true;
    }

    public boolean removeCreatedPost(BasicPost post){
        if(post==null) return false;
        if(this.createdPosts.contains(post)){
            createdPosts.remove(post);
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                firstName + " " + lastName +
                "(" + username + ")}" + " " + role;
    }
}
