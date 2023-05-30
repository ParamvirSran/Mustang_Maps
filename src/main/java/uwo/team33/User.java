package uwo.team33;

import java.util.ArrayList;

/**
 * uwo.team33.User class
 *
 * @author Paramvir Sran
 * @version 1.0
 */
public class User {
    private final boolean isDeveloper;
    private ArrayList<POI> favourites;
    private ArrayList<POI> userCreated;
    private String username;
    private String password;


    /**
     * Constructor
     *
     * @param username username
     * @param password password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.favourites = new ArrayList<>();
        this.userCreated = new ArrayList<>();
        this.isDeveloper = false;
    }

    /**
     * Constructor
     *
     * @param username    username
     * @param password    password
     * @param isDeveloper is developer
     */
    public User(String username, String password, boolean isDeveloper) {
        this.username = username;
        this.password = password;
        this.favourites = new ArrayList<>();
        this.userCreated = new ArrayList<>();
        this.isDeveloper = isDeveloper;
    }

    /**
     * Constructor
     *
     * @param username    username
     * @param password    password
     * @param favourites  favourites
     * @param userCreated user created
     * @param isDeveloper is developer
     */
    public User(String username, String password, ArrayList<POI> favourites, ArrayList<POI> userCreated, boolean isDeveloper) {
        this.username = username;
        this.password = password;
        this.favourites = favourites;
        this.userCreated = userCreated;
        this.isDeveloper = isDeveloper;
    }

    /**
     * Get username
     *
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     *
     * @param username username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password
     *
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     *
     * @param password password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get favourites
     *
     * @return favourites of the user
     */
    public ArrayList<POI> getFavourites() {
        return favourites;
    }


    /**
     * Set favourites
     *
     * @param favourites favourites of the user
     */
    public void setFavourites(ArrayList<POI> favourites) {
        this.favourites = favourites;
    }

    /**
     * Get user created
     *
     * @return user created of the user
     */
    public ArrayList<POI> getUserCreated() {
        return userCreated;
    }

    /**
     * Set user created
     *
     * @param userCreated user created of the user
     */
    public void setUserCreated(ArrayList<POI> userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * Get is developer
     *
     * @return is developer of the user
     */
    public boolean isDeveloper() {
        return isDeveloper;
    }


    /**
     * Add favourite
     *
     * @param poi poi to add
     */
    public void addFavourite(POI poi) {
        favourites.add(poi);
    }

    /**
     * Add user created
     *
     * @param poi poi to add
     */
    public void addUserCreated(POI poi) {
        userCreated.add(poi);

    }

    /**
     * Remove favourite
     *
     * @param poi poi to remove
     */
    public void removeFavourite(POI poi) {
        favourites.remove(poi);
    }

    /**
     * Remove user created
     *
     * @param poi poi to remove
     */
    public void removeUserCreated(POI poi) {
        userCreated.remove(poi);
    }

}