package uwo.team33;

import java.util.ArrayList;

/**
 * this class represents the floors in the building
 *
 * @author Paramvir Sran
 * @version 1.0
 */
public class Floor {
    private int floorNum;
    private String imagePath;
    private ArrayList<POI> points;

    /**
     * floor constructor, no parameters
     */
    public Floor() {
        this.floorNum = -1;
        this.imagePath = "";
        this.points = new ArrayList<POI>();
    }
    
    /**
     * floor constructor that creates a floor with the floor number and map image associated with it
     *
     * @param floorNum  the floor number (i.e. 0 for ground floor)
     * @param imagePath a path to the map image for the floor
     */
    public Floor(int floorNum, String imagePath) {
        this.floorNum = floorNum;
        this.imagePath = imagePath;
        this.points = new ArrayList<POI>();
    }

    /**
     * floor constructor that creates a floor with the floor number, map image, and array of POIs associated with it
     *
     * @param floorNum   the floor number (i.e. 0 for ground floor)
     * @param imagePath  a path to the map image for the floor
     * @param pointsList an array of POIs for this floor
     */
    public Floor(int floorNum, String imagePath, ArrayList<POI> pointsList) {
        this.floorNum = floorNum;
        this.imagePath = imagePath;
        this.points = pointsList;
    }

    /**
     * getter method to get the floor number associated with the floor
     *
     * @return the floor number
     */
    public int getFloorNum() {
        return this.floorNum;
    }

    /**
     * setter method to set the floor number associated with the floor
     *
     * @param floorNum the floor number
     */
    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    /**
     * getter method to get the map image file path of the map associated with the floor
     *
     * @return the map image file path
     */
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * setter method to set the map image file path of the map associated with the floor
     *
     * @param imagePath the map image file path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * getter method to get the array of POIs for this floor
     *
     * @return an array of POIs for this floor
     */
    public ArrayList<POI> getPointsList() {
        return this.points;
    }

    /**
     * setter method to set the array of POIs for this floor
     *
     * @param pointsList an array of POIs for this floor
     */
    public void setPointsList(ArrayList<POI> pointsList) {
        this.points = pointsList;
    }

    /**
     * adds a point of interest to the floor
     *
     * @param point the point of interest to be added
     */
    public void addPoint(POI point) {
        this.points.add(point);
    }

    /**
     * removes a point of interest from the floor
     *
     * @param point the point of interest to be removed
     */
    public void removePoint(POI point) {
        this.points.remove(point);
    }

    /**
     * gets a point of interest from the floor
     *
     * @param pointName the name of the point of interest to be retrieved
     * @return the point of interest
     */
    public POI getPoint(String pointName) {
        for (POI point : this.points) {
            if (point.getName().equals(pointName)) {
                return point;
            }
        }
        return null;
    }

}