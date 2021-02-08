package cz.cvut.fel.ear.covid.service;

import cz.cvut.fel.ear.covid.dao.BasicPostDao;
import cz.cvut.fel.ear.covid.dao.TagDao;
import cz.cvut.fel.ear.covid.model.BasicPost;
import cz.cvut.fel.ear.covid.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TagService {

    private final TagDao dao;

    private final BasicPostDao postDao;

    @Autowired
    public TagService(TagDao dao, BasicPostDao postDao) {
        this.dao = dao;
        this.postDao = postDao;
    }

    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public Tag find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Tag tag) {
        Objects.requireNonNull(tag);
        dao.persist(tag);
    }

    @Transactional
    public void delete(Tag tag) {
        Objects.requireNonNull(tag);
        dao.remove(tag);
    }

    @Transactional
    public void addTagToPost(Tag tag, BasicPost post) {
        Objects.requireNonNull(tag);
        Objects.requireNonNull(post);
        post.addTag(tag);
        tag.addPostToTag(post);
        dao.update(tag);
        postDao.update(post);
    }

    @Transactional
    public void removeTagFromPost(Tag tag, BasicPost post) {
        Objects.requireNonNull(tag);
        Objects.requireNonNull(post);
        post.removeTag(tag);
        tag.removePostFromTag(post);
        dao.update(tag);
        postDao.update(post);
    }
}
