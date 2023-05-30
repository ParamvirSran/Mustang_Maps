package uwo.team33;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
// import javafx.scene.control.ButtonType;
// import javafx.scene.control.Dialog;
// import javafx.scene.control.MenuBar;
// import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Utility {

//    public static ArrayList<JSONObject> getBuildingPOI (String Building) throws IOException, URISyntaxException {
//        URL resource = LoginController.class.getResource("/uwo/team33/poi.json");
//        assert resource != null;
//        Path path = Paths.get(resource.toURI());
//
//        String jsonStr = new String(Files.readAllBytes(path));
//        JSONObject jsonObj = new JSONObject(jsonStr);
//
//        JSONArray pois = jsonObj.getJSONArray("points");
//
//        ArrayList<JSONObject> poiList = new ArrayList<JSONObject>();
//
//        for (int i = 0; i < pois.length(); i++) {
//
//            if ( pois.getJSONObject(i).getString("buildingName").equals(Building)){
//                poiList.add(pois.getJSONObject(i));
//            }
//        }
//        return poiList;
//    }

    public static ArrayList<JSONObject> getPOIs(String building, Integer floor, String layer) throws IOException {

        InputStream inputStream = Utility.class.getResourceAsStream("poi.json");
        if (inputStream == null) {
            throw new IOException("Resource file not found: /poi.json");
        }
        String jsonStr = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));

        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray pois = jsonObj.getJSONArray("points");
        ArrayList<JSONObject> poiList = new ArrayList<>();

        for (int i = 0; i < pois.length(); i++) {
            JSONObject poi = pois.getJSONObject(i);
            if (matchesFilters(poi, building, floor, layer)) {
                poiList.add(poi);
            }
        }
        return poiList;
    }

    private static boolean matchesFilters(JSONObject poi, String building, Integer floor, String layer) {
        Predicate<JSONObject> matchesBuilding = p -> building == null || p.getString("buildingName").equals(building);
        Predicate<JSONObject> matchesFloor = p -> floor == null || p.getInt("floorNum") == floor;
        Predicate<JSONObject> matchesLayer = p -> layer == null || p.getString("layer").equals(layer);

        return matchesBuilding.test(poi) && matchesFloor.test(poi) && matchesLayer.test(poi);
    }

    public static void addPOIToUser(JSONObject poi) throws IOException, URISyntaxException {
        URL resource = LoginController.class.getResource("/uwo/team33/user.json");
        assert resource != null;
        Path path = Paths.get(resource.toURI());
        String jsonStr = new String(Files.readAllBytes(path));
        JSONObject jsonObj = new JSONObject(jsonStr);

        JSONArray users = jsonObj.getJSONArray("users");

        for (int i = 0; i < users.length(); i++) {
            JSONObject jsonObject = users.getJSONObject(i);
            if (jsonObject.get("username").equals(App.currentUser.getUsername())) {
                JSONArray userCreated = jsonObject.getJSONArray("userCreated");
                userCreated.put(poi);
                String formattedJsonStr = jsonObj.toString(2);
                Files.writeString(path, formattedJsonStr);
                break;
            }
        }
    }

    private static String selectedLayerText = "Layer";

    public static String addUserPOI(double[] location, int floorNum, String buildingName) throws IOException, URISyntaxException {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create a POI!");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        TextField name = new TextField();
        name.setPromptText("Name");
        TextField desc = new TextField();
        desc.setPromptText("Descripton");
        String[] layerStrings = {"Accessibility", "Classroom", "Restaurant", "ComputerLab", "CollaborativeRoom", "Library", "Favourite", "UserCreated", "General"};
        MenuBar layer = new MenuBar();
        Menu titleMenu = new Menu(selectedLayerText); //set as title
        
        for (String layerString : layerStrings) {
            MenuItem menu_item = new MenuItem(layerString);
            menu_item.setOnAction(e -> {    //add each layer as option 
                Utility.selectedLayerText = layerString;
                titleMenu.setText(layerString);
            });
            titleMenu.getItems().add(menu_item);
        }
        titleMenu.setDisable(false);
        layer.getMenus().add(titleMenu);
        layer.setPrefWidth(175);

        gridPane.add(name, 0, 0);
        gridPane.add(desc, 0, 2);
        gridPane.add(layer, 0, 4);

        dialog.getDialogPane().setContent(gridPane);

        Optional<ButtonType> result = dialog.showAndWait();


        if ((result.isPresent()) && (result.get() == okButton)) {
            JSONObject poi = new JSONObject();
            String poiName = name.getText();

            poi.put("name", poiName);
            poi.put("buildingName", buildingName);
            poi.put("description", desc.getText());
            poi.put("floorNum", floorNum);
            poi.put("location", location);
            poi.put("layer", Utility.selectedLayerText);
            addPOIToUser(poi);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("POI created successfully");
            alert.show();
            return poiName;
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Did Not Create POI");
            alert.show();
            dialog.close();
            return "";
        }


    }

    public static ArrayList<POI> getUserFavourites() throws URISyntaxException, IOException {

        try {
            URL resource = LoginController.class.getResource("/uwo/team33/user.json");
            Path path = Paths.get(resource.toURI());
            String username = App.currentUser.getUsername();
            StringBuilder jsonString = new StringBuilder();
            FileInputStream inputStream = new FileInputStream(path.toString());
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonString.append(line);

            }
            bufferedReader.close();
            reader.close();
            inputStream.close();

            JSONObject json = new JSONObject(jsonString.toString());

            JSONArray users = json.getJSONArray("users");

            ArrayList<POI> resultList = new ArrayList<POI>();

            // Iterate through all the objects in the users array
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);

                // Check if the "name" field matches the usernanme
                if (user.getString("username").equals(username)) {
                    try {
                        JSONArray favourites = user.getJSONArray("favourites");
                        for (int j = 0; j < favourites.length(); j++) {

                            JSONObject point = favourites.getJSONObject(j);

                            String name = point.getString("name");
                            String description = point.getString("description");
                            JSONArray locJSON = point.getJSONArray("location");
                            double[] location = new double[]{locJSON.getDouble(0), locJSON.getDouble(1)};
                            Layer layer = Layer.valueOf(point.getString("layer"));
                            String buildingName = point.getString("buildingName");
                            int floorNum = point.getInt("floorNum");


                            POI resultPOI = new POI(name, description, layer, location, buildingName, floorNum);
                            resultList.add(resultPOI);
                        }
                    } catch (JSONException e) {
                        JSONArray userCreated = new JSONArray();
                        user.put("favouries", userCreated);
                        users.put(user);
                        String formattedJsonArray = json.toString(2);
                        Files.write(path, formattedJsonArray.getBytes(StandardCharsets.UTF_8));
                    }
                }
            }
            return resultList;

        } catch (IOException e) {

            ErrorBoxController noPOI = new ErrorBoxController();
            noPOI.display("User.json not found");
            System.out.println(e);
        }


        ErrorBoxController noPOI = new ErrorBoxController();
        noPOI.display("Help Feature Coming Soon");
        return null;
    }


    public static ArrayList<POI> getUserPOI() throws URISyntaxException, IOException {

        try {
            URL resource = LoginController.class.getResource("/uwo/team33/user.json");
            Path path = Paths.get(resource.toURI());
            String username = App.currentUser.getUsername();
            StringBuilder jsonString = new StringBuilder();
            FileInputStream inputStream = new FileInputStream(path.toString());
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonString.append(line);

            }
            bufferedReader.close();
            reader.close();
            inputStream.close();

            JSONObject json = new JSONObject(jsonString.toString());

            JSONArray users = json.getJSONArray("users");

            ArrayList<POI> resultList = new ArrayList<POI>();

            // Iterate through all the objects in the users array
            for (int i = 0; i < users.length(); i++) {

                JSONObject user = users.getJSONObject(i);

                // Check if the "name" field matches the usernanme
                if (user.getString("username").equals(username)) {
                    try {
                        JSONArray userCreated = user.getJSONArray("userCreated");
                        for (int j = 0; j < userCreated.length(); j++) {

                            JSONObject point = userCreated.getJSONObject(j);

                            String name = point.getString("name");
                            String description = point.getString("description");
                            JSONArray locJSON = point.getJSONArray("location");
                            double[] location = new double[]{locJSON.getDouble(0), locJSON.getDouble(1)};
                            Layer layer = Layer.valueOf(point.getString("layer"));
                            String buildingName = point.getString("buildingName");
                            int floorNum = point.getInt("floorNum");


                            POI resultPOI = new POI(name, description, layer, location, buildingName, floorNum);
                            resultList.add(resultPOI);
                        }
                    } catch (JSONException e) {
                        JSONArray userCreated = new JSONArray();
                        user.put("userCreated", userCreated);
                        users.put(user);
                        String formattedJsonArray = json.toString(2);
                        Files.write(path, formattedJsonArray.getBytes(StandardCharsets.UTF_8));
                    }
                }
            }

            return resultList;

        } catch (IOException e) {

            ErrorBoxController noPOI = new ErrorBoxController();
            noPOI.display("User Json not found");
            System.out.println(e);
        }


        ErrorBoxController noPOI = new ErrorBoxController();
        noPOI.display("No POI Found");
        return null;
    }


    public static ArrayList<POI> searchForPOI(String searchString) {
        try {
            URL resourceUser = LoginController.class.getResource("/uwo/team33/user.json");
            Path pathUser = Paths.get(resourceUser.toURI());
            FileInputStream inputStreamUser = new FileInputStream(pathUser.toString());

            StringBuilder jsonStringUser = new StringBuilder();
            InputStreamReader readerUser = new InputStreamReader(inputStreamUser, StandardCharsets.UTF_8);
            BufferedReader bufferedReaderUser = new BufferedReader(readerUser);
            String lineUser;
            while ((lineUser = bufferedReaderUser.readLine()) != null) {
                jsonStringUser.append(lineUser);
            }
            bufferedReaderUser.close();
            readerUser.close();
            inputStreamUser.close();

            JSONObject jsonUser = new JSONObject(jsonStringUser.toString());
            JSONArray users = jsonUser.getJSONArray("users");

            ArrayList<POI> resultList = new ArrayList<POI>();

            for (int i=0; i<users.length(); i++) {
                JSONObject jsonObject = users.getJSONObject(i);
                if (jsonObject.get("username").equals(App.currentUser.getUsername())) {
                    JSONArray allUserCreated = jsonObject.getJSONArray("userCreated");

                    for (int j=0; j < allUserCreated.length(); j++) {
                        JSONObject userCreated = allUserCreated.getJSONObject(j);

                        // Check if the "buildingName" field matches the search string
                        boolean buildingNameSearch = userCreated.getString("buildingName").equals(searchString);

                        // Check if the "name" field matches the search string
                        boolean nameSearch = userCreated.getString("name").equals(searchString);

                        // Check if the search string is anywhere in the "description" field
                        int descriptionSearch = userCreated.getString("description").indexOf(searchString);


                        // Check if any of the fields stores the search string
                        if (buildingNameSearch || nameSearch || descriptionSearch>0) {
                            // If any do, return the corresponding "buildingName" field
                            String buildingName = userCreated.getString("buildingName");
                            String name = userCreated.getString("name");
                            String description = userCreated.getString("description");
                            JSONArray locJSON = userCreated.getJSONArray("location");
                            double[] location = new double[]{locJSON.getDouble(0), locJSON.getDouble(1)};
                            Layer layer = Layer.valueOf(userCreated.getString("layer"));
                            int floorNum = userCreated.getInt("floorNum");
                            POI resultPOI = new POI(name, description, layer, location, buildingName, floorNum);
                            resultList.add(resultPOI);
                        }
                    }
                }
            }

            URL resourcePOI = LoginController.class.getResource("/uwo/team33/poi.json");
            Path pathPOI = Paths.get(resourcePOI.toURI());
            FileInputStream inputStreamPOI = new FileInputStream(pathPOI.toString());
            StringBuilder jsonStringPOI = new StringBuilder();
            InputStreamReader readerPOI = new InputStreamReader(inputStreamPOI, StandardCharsets.UTF_8);
            BufferedReader bufferedReaderPOI = new BufferedReader(readerPOI);
            String linePOI;
            while ((linePOI = bufferedReaderPOI.readLine()) != null) {
                jsonStringPOI.append(linePOI);
            }
            bufferedReaderPOI.close();
            readerPOI.close();
            inputStreamPOI.close();

            JSONObject jsonPOI = new JSONObject(jsonStringPOI.toString());
            JSONArray allBuiltInPOIs = jsonPOI.getJSONArray("points");
            for (int j=0; j<allBuiltInPOIs.length(); j++) {
                JSONObject builtInPOI = allBuiltInPOIs.getJSONObject(j);

                // Check if the "buildingName" field matches the search string
                boolean buildingNameSearch = builtInPOI.getString("buildingName").equals(searchString);

                // Check if the "name" field matches the search string
                boolean nameSearch = builtInPOI.getString("name").equals(searchString);

                // Check if the search string is anywhere in the "description" field
                int descriptionSearch = builtInPOI.getString("description").indexOf(searchString);

                // Check if any of the fields stores the search string
                if (buildingNameSearch || nameSearch || descriptionSearch>0) {
                    // If any do, return the corresponding "buildingName" field
                    String name = builtInPOI.getString("name");
                    String description = builtInPOI.getString("description");
                    Layer layer = Layer.valueOf(builtInPOI.getString("layer"));
                    JSONArray locJSON = builtInPOI.getJSONArray("location");
                    double[] location = new double[]{locJSON.getDouble(0), locJSON.getDouble(1)};
                    String buildingName = builtInPOI.getString("buildingName");
                    int floorNum = builtInPOI.getInt("floorNum");
                    POI resultPOI = new POI(name, description, layer, location, buildingName, floorNum);
                    resultList.add(resultPOI);
                }
            }

            return resultList;
        } catch (IOException | URISyntaxException e) {
            ErrorBoxController noPOI = new ErrorBoxController();
            noPOI.display("No JSON File Found");
        }
        ErrorBoxController noPOI = new ErrorBoxController();
        noPOI.display("No POI Found");
        return null;
    }

    public static ArrayList<POI> searchForPOIByBuilding(String searchString) throws URISyntaxException {
        try {
            URL resource = LoginController.class.getResource("/uwo/team33/poi.json");
            Path path = Paths.get(resource.toURI());
            StringBuilder jsonString = new StringBuilder();
            FileInputStream inputStream = new FileInputStream(path.toString());
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonString.append(line);
            }
            bufferedReader.close();
            reader.close();
            inputStream.close();

            JSONObject json = new JSONObject(jsonString.toString());

            JSONArray points = json.getJSONArray("points");

            ArrayList<POI> resultList = new ArrayList<POI>();

            // Iterate through all the objects in the "points" array
            for (int i = 0; i < points.length(); i++) {
                JSONObject point = points.getJSONObject(i);

                // Check if the "name" field matches the search string
                if (point.getString("buildingName").equals(searchString)) {
                    // If it does, return the corresponding "buildingName" field

                    String name = point.getString("name");
                    String description = point.getString("description");
                    JSONArray locJSON = point.getJSONArray("location");
                    double[] location = new double[]{locJSON.getDouble(0), locJSON.getDouble(1)};
                    String buildingName = point.getString("buildingName");
                    int floorNum = point.getInt("floorNum");

                    POI resultPOI = new POI(name, description, location, buildingName, floorNum);
                    resultList.add(resultPOI);
                }
            }
            return resultList;
        } catch (IOException e) {
            ErrorBoxController noPOI = new ErrorBoxController();
            noPOI.display("No JSON File Found");
        }
        ErrorBoxController noPOI = new ErrorBoxController();
        noPOI.display("No POI Found");
        return null;
    }

    /*public static void scrollToPOI(Button poi_button) {
        String poi_name = poi_button.getText();
        JSONObject poiData = null;
        for (int i = 0; i < App.poiList.size(); i++) {
            if (App.poiList.get(i).getName().equals(poi_name)) {
                poiData = App.poiList.get(i).getJSONObject();
            }
        }

        if (poiData != null) {
            try {
                String buildingName = poiData.getString("buildingName");
                int floorNum = poiData.getInt("floorNum");
                JSONArray locJSON = poiData.getJSONArray("location");
                double x = locJSON.getDouble(0);
                double y = locJSON.getDouble(1);
            } catch (JSONException e) {
                System.out.println(e);
            }
            //switch to building if required
            //switch to floor if required
            //scroll to location
        }
    }*/

}