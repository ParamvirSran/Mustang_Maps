package uwo.team33;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class FavouritesController extends Stage {
    Stage favouritesWindow = new Stage();
    private Scene scene;

    @FXML
    private Button cancel_button;
    @FXML
    private Button apply_button;

    @FXML
    private VBox fav1;
    @FXML
    private VBox fav2;
    @FXML
    private VBox fav3;
    @FXML
    private VBox fav4;
    @FXML
    private VBox fav5;



    public void display() {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("FXML/favouritesWindow.fxml"));
        loader.setController(this);
        try {
            scene = new Scene(loader.load(), 400, 500);
        } catch (IOException e) {
            ErrorBoxController error = new ErrorBoxController();
            error.display("Couldn't load Favourites Window");
            return;
        }
        favouritesWindow.setTitle("Favourites Selection");
        favouritesWindow.setScene(scene);
        favouritesWindow.initModality(Modality.APPLICATION_MODAL);
        try {
            this.displayFavourites();
        } catch (Exception e) {
        }
        
        favouritesWindow.showAndWait();
    }
    
    private void displayFavourites() throws URISyntaxException, IOException{
        ArrayList<POI> userFavourites = Utility.getUserFavourites();

        if(userFavourites == null){
            return;
        }else{
            App.currentUser.setFavourites(userFavourites);
        }

        ArrayList<Button> fav_buttons = new ArrayList<>();
        
        for(POI poi : App.currentUser.getFavourites()){
            Button button = new Button(poi.getName());
            button.setWrapText(true);
            fav_buttons.add(button);
            
        }
        
        try {
            fav1.getChildren().add(fav_buttons.get(0));
            fav2.getChildren().add(fav_buttons.get(1));
            fav3.getChildren().add(fav_buttons.get(2));
            fav4.getChildren().add(fav_buttons.get(3));
            fav5.getChildren().add(fav_buttons.get(4));
        } catch (NullPointerException e) {
        }
    }

    @FXML
    private void closeWindow() {
        favouritesWindow.close();
    }

}