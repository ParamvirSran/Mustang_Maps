package uwo.team33;

/**
 * This class represents a building in the Mustang Maps application.
 *
 * @author Paramvir Sran
 * @version 1.0
 */
public class Building {
    private String name;
    private Floor[] floors;
    
    /**
     * building constructor, no parameters
     */
    public Building() {
        this.name = "";
        this.floors = new Floor[0];
    }

    /**
     * building constructor, with parameters
     *
     * @param name   name of the building
     * @param floors floors of the building
     */
    public Building(String name, Floor[] floors) {
        this.name = name;
        this.floors = floors;
    }

    /**
     * get the name of the building
     *
     * @return name of the building
     */
    public String getName() {
        return name;
    }

    /**
     * set the name of the building
     *
     * @param name name of the building
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the floors of the building
     *
     * @return floors of the building
     */
    public Floor[] getFloors() {
        return floors;
    }

    /**
     * set the floors of the building
     *
     * @param floors floors of the building
     */
    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }

    /**
     * get the floor of the building
     *
     * @param floorNum floor number
     * @return floor
     */
    public Floor getFloor(int floorNum) {
        return floors[floorNum];
    }

    /**
     * set the floor of the building
     *
     * @param floorNum floor number
     * @param floor    floor
     */
    public void setFloor(int floorNum, Floor floor) {
        floors[floorNum] = floor;
    }

    /**
     * get the number of floors in the building
     *
     * @return number of floors
     */
    public int getNumFloors() {
        return floors.length;
    }

}