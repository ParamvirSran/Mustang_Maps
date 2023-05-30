package uwo.team33;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WSCController implements Initializable {
    private final String buildingName = "Western Science Centre";


    ArrayList<JSONObject> wsc;


    @FXML
    private VBox built_in_POI;
    @FXML
    private VBox user_POI;
    @FXML
    private TextField search_box;
    @FXML
    private Button home_button;
    @FXML
    private Button layers_button;
    @FXML
    private Button favourites_button;
    @FXML
    private Button addPOI_button;
    @FXML
    private MenuItem campus_menu;
    @FXML
    private MenuItem pab_menu;
    @FXML
    private MenuItem wsc_menu;


    @FXML
    private MenuItem mc_menu;

    @FXML
    private TabPane building;
    @FXML
    private Tab ground_floor;
    private Tab currTab = ground_floor;
    @FXML
    private Tab floor_1;
    @FXML
    private Tab floor_2;
    @FXML
    private Tab floor_3;
    @FXML
    private ScrollPane ground_view;
    @FXML
    private ScrollPane floor1_view;
    @FXML
    private ScrollPane floor2_view;
    @FXML
    private ScrollPane floor3_view;
    @FXML
    private ScrollPane floor4_view;
    @FXML
    private AnchorPane ground_content;
    @FXML
    private AnchorPane floor1_content;
    @FXML
    private AnchorPane floor2_content;
    @FXML
    private AnchorPane floor3_content;
    @FXML
    private AnchorPane floor4_content;
    @FXML
    private Tab floor_4;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.displayBuiltInPOI();
            this.displayUserPOI();
            ArrayList<JSONObject> wsc = Utility.getPOIs(buildingName, 0, null);
            for (JSONObject poi : wsc) {
                String name = poi.getString("name");
                Button button = new Button(name);
                button.setMnemonicParsing(false);
                button.setPrefWidth(175);
                built_in_POI.getChildren().add(button);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        // Add ChangeListener to the TabPane's selection model
        building.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                currTab = newValue;
                updatePOIsForCurrentFloor();
            }
        });
    }


    @FXML
    private void loadHome() throws IOException {
        App.setRoot("homepage");
    }

    @FXML
    private void loadPAB() throws IOException {
        App.setRoot("PABmap");
    }

    @FXML
    private void loadWSC() throws IOException {
        App.setRoot("WSCmap");
    }

    @FXML
    private void loadMC() throws IOException {
        App.setRoot("MCmap");
    }

    @FXML
    private void loadHelp() throws IOException {
        HelpInfoController help = new HelpInfoController();
        help.display();
    }

    @FXML
    private void selectLayers() {
        LayerController layers = new LayerController();
        layers.display();
    }

    @FXML
    private void loadFavourites() {
        FavouritesController favourites = new FavouritesController();
        favourites.display();
    }


    @FXML
    private void addPOI() throws IOException {
        Tab currTab = getCurrTab();
        AnchorPane currMap = getAnchorPane(currTab);
        AddUserPOIController prompt = new AddUserPOIController();
        prompt.display("Select a location on the Map");
        currMap.setOnMouseClicked(event -> getLocation(event));
    }

    @FXML
    private void getLocation(MouseEvent event) {
        Tab currTab = getCurrTab();
        AnchorPane currMap = getAnchorPane(currTab);
        int floor = getFloorNumber(currTab);
        currMap.removeEventHandler(MouseEvent.MOUSE_CLICKED, this::getLocation);
        double x = event.getX();
        double y = event.getY();
        double[] values = {x, y};
        System.out.println(x + ", " + y);
        try {
            String name = Utility.addUserPOI(values, floor, this.buildingName);
            this.displayUserPOI();
            addImageToMap(x, y, name);
        } catch (IOException | URISyntaxException e) {
            ErrorBoxController error = new ErrorBoxController();
            error.display("Could not add POI");
        }

    }

    private void addImageToMap(double x, double y, String name) throws URISyntaxException {

        //if the user canceled poi creation
        if (name.equals("")) {
            return;
        }

        URL resource = LoginController.class.getResource("/uwo/team33/pictures/mapPin.png");
        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: /uwo/team33/pictures/mapPin.png");
        }
        Image image = new Image(resource.toString());


        ImageView poi = new ImageView(image);
        poi.setFitHeight(20);
        poi.setFitWidth(15);
        poi.setLayoutX(x - 7);
        poi.setLayoutY(y - 17);
        AnchorPane currMap = getAnchorPane(getCurrTab());


        Label label = new Label(name);
        label.setLayoutX(x + 8);
        label.setLayoutY(y - 20);
        currMap.getChildren().addAll(poi, label);

    }

    @FXML
    private void handleZoom(ScrollEvent event) {
        ScrollPane scrollPane = (ScrollPane) event.getSource();
        AnchorPane anchorPane = (AnchorPane) scrollPane.getContent();

        if (event.isControlDown()) { //detects scrolling action
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 1 / zoomFactor; //reverse zoom
            }
            anchorPane.setScaleX(anchorPane.getScaleX() * zoomFactor);
            anchorPane.setScaleY(anchorPane.getScaleY() * zoomFactor);
            event.consume();
        }
    }


    private Tab getCurrTab() {
        currTab = building.getSelectionModel().getSelectedItem();
        return currTab;
    }

    @FXML
    private AnchorPane getAnchorPane(Tab currTab) {
        if (currTab == ground_floor) {
            return ground_content;
        } else if (currTab == floor_1) {
            return floor1_content;
        } else if (currTab == floor_2) {
            return floor2_content;
        } else if (currTab == floor_3) {
            return floor3_content;
        } else if (currTab == floor_4) {
            return floor4_content;
        } else {
            System.out.println(currTab.getText());
            ErrorBoxController error = new ErrorBoxController();
            error.display("Map not found");
            return null;
        }
    }

    private void displayBuiltInPOI() {

        try {
            ArrayList<POI> WSCPoints = Utility.searchForPOIByBuilding(this.buildingName);

            if (WSCPoints == null) {
                return;
            }

            ArrayList<Button> poi_buttons = new ArrayList<>();
            for (POI poi : WSCPoints) {
                if (poi.getFloor() == this.getFloorNumber(this.getCurrTab())) {
                    addImageToMap(poi.getLocation()[0], poi.getLocation()[1], poi.getName());
                }
                Button button = new Button(poi.getName());
                poi_buttons.add(button);
                button.setPrefWidth(175);
            }

            this.user_POI.getChildren().clear();
            for (Button button : poi_buttons) {
                this.built_in_POI.getChildren().add(button);
            }
        } catch (Exception e) {

        }


    }


    private void displayUserPOI() throws URISyntaxException, IOException {

        ArrayList<POI> userCreatedPOI = Utility.getUserPOI();

        if (userCreatedPOI == null) {
            return;
        } else {
            App.currentUser.setUserCreated(userCreatedPOI);
        }

        ArrayList<Button> poi_buttons = new ArrayList<>();
        for (POI poi : App.currentUser.getUserCreated()) {
            if (poi.getBuilding().equals(this.buildingName)) {
                addImageToMap(poi.getLocation()[0], poi.getLocation()[1], poi.getName());
            }
            Button button = new Button(poi.getName());
            poi_buttons.add(button);
            button.setPrefWidth(175);
        }

        this.user_POI.getChildren().clear();
        for (Button button : poi_buttons) {
            this.user_POI.getChildren().add(button);
        }

    }

    @FXML
    private int getFloorNumber(Tab currTab) {
        if (currTab == ground_floor) {
            return 0;
        } else if (currTab == floor_1) {
            return 1;
        } else if (currTab == floor_2) {
            return 2;
        } else if (currTab == floor_3) {
            return 3;
        } else if (currTab == floor_4) {
            return 4;
        } else {
            ErrorBoxController error = new ErrorBoxController();
            error.display("Floor not found");
            return -1;
        }
    }
/*

    private void updatePOIsForCurrentFloor() {
        try {
            // Get the AnchorPane for the current floor
            AnchorPane currMap = getAnchorPane(currTab);

            if (currMap != null) {
                // Remove only the POI nodes
                currMap.getChildren().removeIf(node -> node.getStyleClass().contains("poi-node"));

                // Display built-in and user-created POIs for the current floor
                JSONArray allPOIs = getJsonArrayFromJSONFile("src/main/resources/uwo/team33/pictures/POIs.json");
                ArrayList<JSONObject> currentFloorPOIs = new ArrayList<>();

                for (int i = 0; i < allPOIs.length(); i++) {
                    JSONObject poi = allPOIs.getJSONObject(i);
                    if (poi.getString("buildingName").equals(buildingName) && poi.getInt("floorNum") == getFloorNumber(currTab)) {
                        currentFloorPOIs.add(poi);
                    }
                }

                // Clear the built_in_POI VBox
                built_in_POI.getChildren().clear();

                // Repopulate the built_in_POI VBox with current floor POIs
                for (JSONObject poi : currentFloorPOIs) {
                    String name = poi.getString("name");
                    double x = poi.getJSONArray("location").getDouble(0);
                    double y = poi.getJSONArray("location").getDouble(1);

                    Button button = new Button(name);
                    button.setMnemonicParsing(false);
                    button.setPrefWidth(175);

                    // Add event listener for built-in POI buttons
                    button.setOnAction(event -> {
                        // Perform an action when the button is clicked, e.g., show additional information about the POI
                        // or navigate to the POI on the map.
                        System.out.println("Button for built-in POI " + name + " clicked.");
                    });

                    built_in_POI.getChildren().add(button);

                    // Add built-in POI ImageView and Label to the map
                    addBuiltInPOIToMap(x, y, name);
                }
            } else {
                // Handle the case when currMap is null
                System.out.println("Error: AnchorPane for the current floor is null.");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
*/

    private void updatePOIsForCurrentFloor() {
        try {
            // Get the AnchorPane for the current floor
            AnchorPane currMap = getAnchorPane(currTab);
            this.displayBuiltInPOI();
            if (currMap != null) {
                // Remove only the POI nodes
                currMap.getChildren().removeIf(node -> node.getStyleClass().contains("poi-node"));

                // Display built-in and user-created POIs for the current floor
                ArrayList<JSONObject> currentFloorPOIs = Utility.getPOIs(buildingName, getFloorNumber(currTab), null);

                // Clear the built_in_POI VBox
                built_in_POI.getChildren().clear();

                // Repopulate the built_in_POI VBox with current floor POIs
                for (JSONObject poi : currentFloorPOIs) {
                    String name = poi.getString("name");
                    Button button = new Button(name);
                    button.setMnemonicParsing(false);
                    button.setPrefWidth(175);
                    built_in_POI.getChildren().add(button);
                }
            } else {
                // Handle the case when currMap is null
                System.out.println("Error: AnchorPane for the current floor is null.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void searchPOI() throws IOException {
        SearchResultsController results = new SearchResultsController();
        String toSearch = search_box.getText();
        ArrayList<POI> searchResults = Utility.searchForPOI(toSearch);
        results.display(searchResults);
    }
}