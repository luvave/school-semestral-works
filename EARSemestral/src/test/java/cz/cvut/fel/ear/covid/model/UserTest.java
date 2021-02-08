package cz.cvut.fel.ear.covid.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void addAnyPostToUsersCreatedPostsForTheFirstTime(){
        User user = new User();
        Treatment treatment = new Treatment();
        treatment.setTitle("Sample Treatment post");
        user.addCreatedPost(treatment);
        assertEquals(1, user.getCreatedPosts().size());
    }
}
