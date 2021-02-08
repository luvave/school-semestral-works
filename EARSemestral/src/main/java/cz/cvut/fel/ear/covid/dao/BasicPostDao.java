package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Tag;
import cz.cvut.fel.ear.covid.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Basic;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class BasicPostDao extends BaseDao<BasicPost> {

    protected BasicPostDao() {
        super(BasicPost.class);
    }

    public List<BasicPost> findAllByAuthor(User author){
        Objects.requireNonNull(author);
        try{
            return em.createNamedQuery("BasicPost.findByAuthor", BasicPost.class).setParameter("author",author).getResultList();
        } catch (NoResultException e) {
            throw new NoResultException("No posts from this author");
        }

    }

    public List<BasicPost> findAllByTag(Tag tag){
        Objects.requireNonNull(tag);
        try{
        return em.createNamedQuery("BasicPost.findWithTag", BasicPost.class).setParameter("tag",tag).getResultList();
        } catch (NoResultException e) {
            throw new NoResultException("No posts with this tag");
        }
    }

}
