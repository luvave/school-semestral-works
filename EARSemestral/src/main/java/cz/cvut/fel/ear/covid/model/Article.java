package cz.cvut.fel.ear.covid.model;

import javax.persistence.*;
import java.awt.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "Article.findByAPostId", query = "SELECT a FROM Article a WHERE a.associatedPost.id = :id ")
})
public class Article extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private String title;

    @Basic(optional = true)
    @Column(nullable = true)
    private String body;

    @Basic(optional = true)
    @Column(nullable = true)
    private String imageSource;

    @OneToOne
    private BasicPost associatedPost;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public BasicPost getAssociatedPost() {
        return associatedPost;
    }

    public void setAssociatedPost(BasicPost associatedPost) {
        this.associatedPost = associatedPost;
    }
}
