package uwo.team33;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UserTest {

    @Test
    public void testGetUsername() {
        User user = new User("testuser", "testpassword");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    public void testSetUsername() {
        User user = new User("testuser", "testpassword");
        user.setUsername("newuser");
        assertEquals("newuser", user.getUsername());
    }

    @Test
    public void testGetPassword() {
        User user = new User("testuser", "testpassword");
        assertEquals("testpassword", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        User user = new User("testuser", "testpassword");
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void testGetFavourites() {
        User user = new User("testuser", "testpassword");
        ArrayList<POI> favourites = new ArrayList<>();
        favourites.add(new POI("POI 1", null, null, null));
        user.setFavourites(favourites);
        assertEquals(favourites, user.getFavourites());
    }

    @Test
    public void testAddFavourite() {
        User user = new User("testuser", "testpassword");
        POI poi = new POI("POI 1", null, null, null);
        user.addFavourite(poi);
        assertTrue(user.getFavourites().contains(poi));
    }

    @Test
    public void testRemoveFavourite() {
        User user = new User("testuser", "testpassword");
        POI poi = new POI("POI 1", null, null, null);
        user.addFavourite(poi);
        user.removeFavourite(poi);
        assertFalse(user.getFavourites().contains(poi));
    }

    @Test
    public void testGetUserCreated() {
        User user = new User("testuser", "testpassword");
        ArrayList<POI> userCreated = new ArrayList<>();
        userCreated.add(new POI("POI 1", null, null, null));
        user.setUserCreated(userCreated);
        assertEquals(userCreated, user.getUserCreated());
    }

    @Test
    public void testAddUserCreated() {
        User user = new User("testuser", "testpassword");
        POI poi = new POI("POI 1", null, null, null);
        user.addUserCreated(poi);
        assertTrue(user.getUserCreated().contains(poi));
    }

    @Test
    public void testRemoveUserCreated() {
        User user = new User("testuser", "testpassword");
        POI poi = new POI("POI 1", null, null, null);
        user.addUserCreated(poi);
        user.removeUserCreated(poi);
        assertFalse(user.getUserCreated().contains(poi));
    }

    @Test
    public void testIsDeveloper() {
        User user = new User("testuser", "testpassword");
        assertFalse(user.isDeveloper());
    }

    @Test
    public void testIsDeveloperTrue() {
        User user = new User("testuser", "testpassword", true);
        assertTrue(user.isDeveloper());
    }

}
