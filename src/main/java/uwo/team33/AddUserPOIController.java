package uwo.team33;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddUserPOIController extends Stage {

    Stage promptWindow = new Stage();
    @FXML
    private Button cont_button;
    @FXML
    private Label instructions;
    private Scene scene;

    public void display(String message) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("FXML/addUserPOIPrompt.fxml"));
        loader.setController(this);
        try {
            scene = new Scene(loader.load(), 400, 175);
        } catch (IOException e) {
            ErrorBoxController error = new ErrorBoxController();
            error.display("Prompt Load Failed");
        }
        instructions.setText(message);
        promptWindow.setTitle("Add POI");
        promptWindow.setScene(scene);
        promptWindow.initModality(Modality.APPLICATION_MODAL);
        promptWindow.showAndWait();
    }


    @FXML
    private void closeWindow() {
        promptWindow.close();
    }

}