package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.model.Article;
import cz.cvut.fel.ear.covid.model.BasicPost;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Objects;

@Repository
public class ArticleDao extends BaseDao<Article>{

    protected ArticleDao() {
        super(Article.class);
    }

    public Article findByAPostId(Integer id){
        Objects.requireNonNull(id);

        return em.createNamedQuery("Article.findByAPostId", Article.class).setParameter("id",id).getSingleResult();


    }
}
