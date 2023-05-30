

module uwo.team33 {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive org.json;

    opens uwo.team33 to javafx.fxml;
    exports uwo.team33;
}

