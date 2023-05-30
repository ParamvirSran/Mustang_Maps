package uwo.team33;

/**
 * this class represents a point of interest on the map
 *
 * @author Paramvir Sran
 * @version 1.0
 */
public class POI {
    private String name;
    private String description;
    private Layer layer;
    private double[] location;
    private String buildingName;
    private int floorNum;

    
    /**
     * uwo.team33.POI constructor for when they name, description, room number, layer, location, and icon file path are not set
     */
    public POI() {
        this.name = "";
        this.description = "";
        this.layer = null;
        this.location = null;
        this.buildingName = "";
        this.floorNum = -1;
    }


    /**
     * uwo.team33.POI constructor for when they name, description, room number, layer, location, and icon file path are set
     */
    public POI(String name, String description, Layer layer, double[] location) {
        this.name = name;
        this.description = description;
        this.layer = layer;
        this.location = location;
        this.buildingName = "";
        this.floorNum = -1;
    }

    /**
     * uwo.team33.POI constructor for when they name, description, room number, layer, location, icon file path, building name, and floor number are set
     */
    public POI(String name, String description, Layer layer, double[] location, String buildingName, int floorNum) {
        this.name = name;
        this.description = description;
        this.layer = layer;
        this.location = location;
        this.buildingName = buildingName;
        this.floorNum = floorNum;
    }

    

    public POI(String name, String description, double[] location, String buildingName, int floorNum) {
        this.name = name;
        this.description = description;
        this.layer = Layer.General;
        this.location = location;
        this.buildingName = buildingName;
        this.floorNum = floorNum;
    }


    /**
     * getter method to get the name of the uwo.team33.POI
     *
     * @return a string which contains the name of the uwo.team33.POI
     */
    public String getName() {
        return this.name;
    }

    /**
     * setter method to set the name of the uwo.team33.POI
     *
     * @param name a string which will be set as the uwo.team33.POI name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter method to get the description of the uwo.team33.POI
     *
     * @return a string which contains the description of the uwo.team33.POI
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * setter method to set the description of the uwo.team33.POI
     *
     * @param description a string which will be set as the uwo.team33.POI description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter method to get the layer of the uwo.team33.POI
     *
     * @return a layer which contains the layer of the uwo.team33.POI
     */
    public Layer getLayer() {
        return this.layer;
    }

    /**
     * setter method to set the layer of the uwo.team33.POI
     *
     * @param layer a layer which will be set as the uwo.team33.POI's layer
     */
    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    /**
     * getter method to get the location of the uwo.team33.POI on a map image file
     *
     * @return coordinates on a map image file which contains the location of the uwo.team33.POI
     */
    public double[] getLocation() {
        return this.location;
    }

    /**
     * setter method to set the location of the uwo.team33.POI on a map image file
     *
     * @param location coordinates on a map image file which will be set as the uwo.team33.POI's location
     */
    public void setLocation(double[] location) {
        this.location = location;
    }


    /**
     * getter method to get the building associated with the uwo.team33.POI
     *
     * @return the name of the building
     */
    public String getBuilding() {
        return this.buildingName;
    }

    /**
     * setter method to set the building associated with the uwo.team33.POI
     *
     * @param buildingName the name of the building
     */
    public void setBuilding(String buildingName) {
        this.buildingName = buildingName;
    }

    /**
     * getter method to get the floor number of the uwo.team33.POI
     *
     * @return an integer corresponding to the floor of the uwo.team33.POI
     */
    public int getFloor() {
        return this.floorNum;
    }

    /**
     * setter method to set the floor number of the uwo.team33.POI
     *
     * @param floorNum an integer corresponding to the floor of the uwo.team33.POI
     */
    public void setFloor(int floorNum) {
        this.floorNum = floorNum;
    }
}