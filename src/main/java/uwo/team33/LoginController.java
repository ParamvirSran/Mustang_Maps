package uwo.team33;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class LoginController {

    @FXML
    private Button sign_in_button;


    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private Button new_user_button;

    private static boolean checkUsername(String username, String password) {
        String jsonStr;
        try {
            URL resource = LoginController.class.getResource("/uwo/team33/user.json");
            Path path = Paths.get(resource.toURI());
            jsonStr = new String(Files.readAllBytes(path));

            if(jsonStr.equals(null)){
                return false;
            }else{
                return checkJSON(username, password);
            }
            
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkJSON(String username, String password) throws IOException, URISyntaxException {
        URL resource = LoginController.class.getResource("/uwo/team33/user.json");
        assert resource != null;
        Path path = Paths.get(resource.toURI());

        String jsonStr = new String(Files.readAllBytes(path));
        JSONObject jsonObj = new JSONObject(jsonStr);

        JSONArray users = jsonObj.getJSONArray("users");

        
        for (int i = 0; i < users.length(); i++) {
            JSONObject jsonObject = users.getJSONObject(i);
            String name = jsonObject.getString("username");
            String pass = jsonObject.getString("password");
            if (name.equals(username) && pass.equals(password)) {
                return true;
            }
        }

        return false;
    }

    private static void createUserJSON(String username, String password) throws IOException, URISyntaxException {
        URL resource = LoginController.class.getResource("/uwo/team33/user.json");
        assert resource != null;
        Path path = Paths.get(resource.toURI());

        String jsonStr = new String(Files.readAllBytes(path));
        JSONObject jsonObj = new JSONObject(jsonStr);

        JSONArray users = jsonObj.getJSONArray("users");

        JSONObject newUser = new JSONObject();
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("isDeveloper", false);
        users.put(newUser);

        String formattedJsonStr = jsonObj.toString(2);

        Files.write(path, formattedJsonStr.getBytes());
    }

    @FXML
    private void signIn() throws IOException {


        String username = loginField.getText();
        String password = encryptPassword(username, passwordField.getText());

        boolean existingUser = checkUsername(username, password);

        if (existingUser) {
            App.currentUser = new User(username, password);
            App.setRoot("homepage");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Account with that username or password");
            alert.show();
        }
    }

    @FXML
    private void createNewAccount() throws IOException, URISyntaxException {


        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create New Account");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
        
        TextField username = new TextField();
        username.setPromptText("New Username");
        PasswordField password = new PasswordField();
        password.setPromptText("New Password");
        

        gridPane.add(username, 0, 0);
        gridPane.add(password, 0, 2);

        dialog.getDialogPane().setContent(gridPane);


        Optional<ButtonType> result = dialog.showAndWait();

        //check if either field is empty and the button has been pressed 
        if ((result.isPresent()) && (result.get() == okButton) && !((username.getText().equals("") || password.getText().equals("")))) {
            createUserJSON(username.getText(), encryptPassword(username.getText(), password.getText()));
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Account created successfully");
            alert.show();
            dialog.close();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Didnt Create Account");
            alert.show();
            dialog.close();
        }


    }

    // Given a username and password,
    // generate an encrypted password

    // This was achieved using SHA-256 encoding, with a salt.
    // The encoding alone isn't extremely secure, since SHA-256
    // lookup tables can be used, but by using the username as
    // a salt, we can eradicate this issue. Even though the
    // username is stored as plaintext, any attacker would
    // now need to bruteforce every password, and every bruteforce
    // attempt on a password would be mutually exclusive to another
    // password, meaning that the attacker would need to run a
    // bruteforce algorithm for every user separately, greatly
    // improving security.
    private static String encryptPassword(String username, String password) {

        String hashedPassword = null;
        try {
            // Do some SHA-256 magic, and apply our salt from the parameter username
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(username.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            ErrorBoxController error = new ErrorBoxController();
            error.display("Error encrypting password.");
        }
        return hashedPassword;
    }
}