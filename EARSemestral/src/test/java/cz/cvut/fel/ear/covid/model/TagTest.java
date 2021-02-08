package cz.cvut.fel.ear.covid.model;

import org.junit.Assert;
import org.junit.Test;

public class TagTest {

    @Test
    public void successfullyAddPostToTag(){
        Tag tag = new Tag();
        tag.setName("test");

        Symptom post = new Symptom();

        Assert.assertTrue(tag.addPostToTag(post));
        Assert.assertEquals(1, tag.getPosts().size());
    }

    @Test
    public void successfullyRemovePostFromTag(){
        Tag tag = new Tag();
        tag.setName("test");

        Symptom post = new Symptom();

        Assert.assertTrue(tag.addPostToTag(post));
        Assert.assertEquals(1, tag.getPosts().size());

        Assert.assertTrue(tag.removePostFromTag(post));
        Assert.assertEquals(0, tag.getPosts().size());
    }


}
