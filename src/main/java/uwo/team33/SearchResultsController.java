package uwo.team33;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList; 

public class SearchResultsController extends Stage {
    @FXML
    private VBox results;
    Stage resultsWindow = new Stage();
    Scene scene;

    @FXML Button cancel_button;

    public void display(ArrayList<POI> searchResults) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("FXML/searchResults.fxml"));
        loader.setController(this);
        ArrayList<Button> result_buttons = new ArrayList<>();
        try {
            scene = new Scene(loader.load(), 400, 500);
            for (POI poi : searchResults) {
                Button button = new Button(poi.getName());
                button.setPrefWidth(175);
                result_buttons.add(button);
            }
        } catch (IOException e){
            ErrorBoxController error = new ErrorBoxController();
            error.display("Couldn't load Search Results");
            return;
        }
        if (result_buttons.size() == 0) {
            ErrorBoxController noPOI = new ErrorBoxController();
            noPOI.display("No POI Found");
        } else {
            for (Button button : result_buttons) {
                this.results.getChildren().add(0, button);
            }
            resultsWindow.setTitle("Search Results");
            resultsWindow.setScene(scene);
            resultsWindow.initModality(Modality.APPLICATION_MODAL);
            resultsWindow.showAndWait();
        }
    }

    @FXML
    private void closeWindow() {
        resultsWindow.close();
    }

}