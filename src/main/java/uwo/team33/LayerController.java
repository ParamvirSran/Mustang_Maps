package uwo.team33;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LayerController extends Stage {
    Stage layerWindow = new Stage();
    private Scene scene;

    @FXML
    private Button cancel_button;
    @FXML
    private Button apply_button;


    public void display() {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("FXML/layerWindow.fxml"));
        loader.setController(this);
        try {
            scene = new Scene(loader.load(), 400, 500);
        } catch (IOException e) {
            ErrorBoxController error = new ErrorBoxController();
            error.display("Couldn't load Layers Window");
        }
        layerWindow.setTitle("Layer Selection");
        layerWindow.setScene(scene);
        layerWindow.initModality(Modality.APPLICATION_MODAL);
        layerWindow.showAndWait();
    }

    @FXML
    private void closeWindow() {
        layerWindow.close();
    }

}