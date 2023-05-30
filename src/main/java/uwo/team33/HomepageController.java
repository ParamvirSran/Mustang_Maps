package uwo.team33;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
//import javafx.scene.transform.Scale;
import java.util.ResourceBundle;

import org.json.JSONObject;

public class HomepageController implements Initializable {
    ArrayList<JSONObject> campus;
    @FXML 
    private VBox built_in_POI;
    @FXML
    private TextField search_box;
    @FXML
    private Button logout_button;
    @FXML
    private Button favourites_button;
    @FXML
    private TabPane building;
    @FXML
    private Tab ground_floor;
    @FXML
    private MenuItem mc_menu;
    @FXML
    private MenuItem pab_menu;
    @FXML
    private MenuItem wsc_menu;
    @FXML
    private ScrollPane map_view;
    @FXML
    private AnchorPane content_pane;
    @FXML
    private ImageView help_icon;
    
    private Tab currTab = ground_floor;
    private String buildingName = "Main Campus";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Replace the old method call with the new getPOIs method
            ArrayList<JSONObject> campus = Utility.getPOIs(buildingName, 0, null);
            this.displayUserPOI();
            for (JSONObject poi : campus) {
                String name = poi.getString("name");
                Button button = new Button(name);
                button.setMnemonicParsing(false);
                button.setPrefWidth(175);
                if (this.built_in_POI != null) {
                    this.built_in_POI.getChildren().add(button);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } 

    }
    
    private void displayUserPOI() throws URISyntaxException, IOException{

        ArrayList<POI> userCreatedPOI = Utility.getUserPOI();
        if (userCreatedPOI == null){
            return;
        }else{
            App.currentUser.setUserCreated(userCreatedPOI);
        }
        
        ArrayList<Button> poi_buttons = new ArrayList<>();
        if (userCreatedPOI != null) {
            for (POI poi : userCreatedPOI){
                if(poi.getBuilding().equals(this.buildingName)){
                    addImageToMap(poi.getLocation()[0], poi.getLocation()[1], poi.getName());
                }
                Button button = new Button(poi.getName());
                button.setPrefWidth(175);
                poi_buttons.add(button);
            }

            if (this.built_in_POI != null){
                this.built_in_POI.getChildren().clear();
            }
            for(Button button : poi_buttons) {
                this.built_in_POI.getChildren().add(button);
            }

        }
    }
    
    private void addImageToMap(double x, double y, String name) throws URISyntaxException{

        //if the user canceled poi creation
        if(name.equals("")){
            return;
        }

        URL resource = LoginController.class.getResource("/uwo/team33/pictures/mapPin.png");
        assert resource != null;
    
        Path path = Paths.get(resource.toURI());
        Image image = new Image(path.toString());

        ImageView poi = new ImageView(image);
        poi.setFitHeight(20);
        poi.setFitWidth(15);
        poi.setLayoutX(x - 7);
        poi.setLayoutY(y - 17);
        AnchorPane currMap = getAnchorPane();
      
       
        Label label = new Label(name);
        label.setLayoutX(x + 8);
        label.setLayoutY(y - 20);
        currMap.getChildren().addAll(poi,label);
        
    }

    @FXML
    private AnchorPane getAnchorPane() {
        return content_pane;
    }

    @FXML
    private Tab getCurrTab() {
        return currTab;
    }

    @FXML
    private void logOut() throws IOException {
        App.currentUser = null;
        App.setRoot("login");
    }

    @FXML
    private void loadMCMap() throws IOException {
        App.setRoot("MCmap");

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
    private void loadHelp() throws IOException {
        HelpInfoController help = new HelpInfoController();
        help.display();
    }

    @FXML
    private void loadFavourites() {
        FavouritesController favourites = new FavouritesController();
        favourites.display();
    }

    // @FXML
    // private void getLocation(MouseEvent event) {
    //     double x = event.getX();
    //     double y = event.getY();
    //     System.out.println("X: " + x + ", Y: " + y);
    // }

    @FXML
    private void handleZoom(ScrollEvent event) {
        if (event.isControlDown()) { //detects scrolling action
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 1 / zoomFactor; //reverse zoom
            }
            content_pane.setScaleX(content_pane.getScaleX() * zoomFactor);
            content_pane.setScaleY(content_pane.getScaleY() * zoomFactor);
            event.consume();
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