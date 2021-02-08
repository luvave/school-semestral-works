package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.dao.ArticleDao;
import cz.cvut.fel.ear.covid.dao.BasicPostDao;
import cz.cvut.fel.ear.covid.model.Article;
import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Symptom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleDao articleDao;

    @Autowired
    public ArticleService(BasicPostDao basicPostDao, ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    @Transactional(readOnly = true)
    public Article find(Integer id) {
        return articleDao.find(id);
    }

    @Transactional
    public void persist(Article post) {
        articleDao.persist(post);
    }

    @Transactional
    public void update(Article post) {
        articleDao.update(post);
    }

    @Transactional
    public void delete(Article post) { articleDao.remove(post);}

}
