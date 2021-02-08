package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.dao.ArticleDao;
import cz.cvut.fel.ear.covid.dao.BasicPostDao;
import cz.cvut.fel.ear.covid.dao.UserDao;
import cz.cvut.fel.ear.covid.model.Article;
import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class BasicPostService {

    private final BasicPostDao dao;

    private final ArticleDao articleDao;

    private final TagService tagService;

    @Autowired
    public BasicPostService(BasicPostDao dao, ArticleDao articleDao,TagService tagService) {
        this.dao = dao;
        this.articleDao = articleDao;
        this.tagService = tagService;
    }

    @Transactional(readOnly = true)
    public List<BasicPost> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public BasicPost find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(BasicPost post) {
        dao.persist(post);
    }

    @Transactional
    public void update(BasicPost post) {
        dao.update(post);
    }

    @Transactional
    public void delete(BasicPost post) {
        dao.remove(post);
    }

    @Transactional
    public List<BasicPost> findByTagId(Integer id){
        return dao.findAllByTag(tagService.find(id));
    }

    @Transactional
    public boolean haveArticle(BasicPost post){
        try{
            articleDao.findByAPostId(post.getId());
        }catch(NoResultException e){
            return false;
        }
        return true;
    }

    @Transactional
    public Article getArticle(BasicPost post){
        if(articleDao.findByAPostId(post.getId())!=null){
            return articleDao.findByAPostId(post.getId());
        }
        return null;
    }
}
