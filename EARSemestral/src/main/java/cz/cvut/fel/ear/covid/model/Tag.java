package cz.cvut.fel.ear.covid.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @OrderBy("title")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<BasicPost> posts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BasicPost> getPosts() {
        return posts;
    }

    public void setPosts(List<BasicPost> posts) {
        this.posts = posts;
    }

    public boolean addPostToTag(BasicPost post){
        if(post==null) return false;
        if(this.posts == null){
            this.posts = new ArrayList<>();
        }
        posts.add(post);
        return true;
    }

    public boolean removePostFromTag(BasicPost post){
        if(post==null) return false;
        if(posts.contains(post)){
            posts.remove(post);
        }
        return true;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                "}";
    }
}
