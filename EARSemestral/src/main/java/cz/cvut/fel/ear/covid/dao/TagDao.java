package cz.cvut.fel.ear.covid.dao;

import cz.cvut.fel.ear.covid.model.Tag;
import org.springframework.stereotype.Repository;

@Repository
public class TagDao extends BaseDao<Tag>{

    protected TagDao() {
        super(Tag.class);
    }
}
