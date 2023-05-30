package uwo.team33;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpInfoController extends Stage {

    Stage helpWindow = new Stage();
    @FXML
    private Button close_window;
    @FXML
    private Label contact;
    private Scene scene;

    public void display() {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("FXML/helpInfo.fxml"));
        loader.setController(this);
        try {
            scene = new Scene(loader.load(), 500, 250);
        } catch (IOException e) {
            System.out.println("Something went wrong\n");
        }
        helpWindow.setTitle("Mustang Maps Info");
        helpWindow.setScene(scene);
        helpWindow.initModality(Modality.APPLICATION_MODAL);
        helpWindow.showAndWait();
    }


    @FXML
    private void closeWindow() {
        helpWindow.close();
    }

}