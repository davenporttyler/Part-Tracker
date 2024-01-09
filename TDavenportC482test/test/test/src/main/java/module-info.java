module proj.test {
    requires javafx.controls;
    requires javafx.fxml;


    opens proj.test to javafx.fxml;
    exports proj.test;
}