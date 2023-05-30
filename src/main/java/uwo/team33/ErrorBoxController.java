package uwo.team33;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorBoxController extends Stage {

    Stage errorWindow = new Stage();
    @FXML
    private Button close_window;
    @FXML
    private Label error_message;
    private Scene scene;

    public void display(String message) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("FXML/errorbox.fxml"));
        loader.setController(this);
        try {
            scene = new Scene(loader.load(), 400, 175);
        } catch (IOException e) {
            System.out.println("Something went wrong\n");
        }
        error_message.setText(message);
        errorWindow.setTitle("Error");
        errorWindow.setScene(scene);
        errorWindow.initModality(Modality.APPLICATION_MODAL);
        errorWindow.showAndWait();
    }


    @FXML
    private void closeWindow() {
        errorWindow.close();
    }

}