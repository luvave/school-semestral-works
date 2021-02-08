package cz.cvut.fel.ear.covid.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "BasicPost.findByAuthor", query = "SELECT p FROM BasicPost p WHERE p.author = :author "),
        @NamedQuery(name = "BasicPost.findWithTag", query = "SELECT p FROM BasicPost p WHERE :tag MEMBER OF p.tags")
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "BLOG_TYPE")
public abstract class BasicPost extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private String title;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User author;

    @ManyToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
    @OrderBy("name")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Tag> tags;


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public boolean addTag(Tag tag){
        if(tag==null) return false;
        if(this.tags == null){
            this.tags = new ArrayList<>();
        }
        tags.add(tag);
        return true;
    }

    public boolean removeTag(Tag tag){
        if(tag==null) return false;
        if(tags.contains(tag)){
            tags.remove(tag);
        }
        return true;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
