package proj.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * Main Application Class
 * @author TDavenport
 *
 * JAVADOCS FOLDER LOCATION: Javadocs exported and uploaded as a seperate Zip file.
 *
 * FUTURE ENHANCEMENTS: I would add 2 additional features to this project. Firstly I would make another button on the main
 * screen that is called "More Details", and it would show more part details. Currently only a small subset of the part attributes
 * shown. When a part is selected it would bring up a new window or changes the table view to toggle between the 2 views so a
 * user can see all the details of a part instead if they would like.
 * Secondly I would create a part usage monitoring metric that is displayed to show the most used parts. Since multiple products
 * can all have the same part it creates different amounts of demand for any one specific part. This would allow buisinesses to use
 * this metric to better source thier parts to create products.
 */
public class MainApplication extends Application {

    /**
     * Launches Main Application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Loads Main Form View FXML and sets scene / stage.
     * @param mainStage
     * @throws IOException
     */
    @Override
    public void start(Stage mainStage) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
        Scene scene = new Scene(root);
        mainStage.setTitle("C482 TDavenport Inventory Management System");
        mainStage.setScene(scene);
        mainStage.show();

    }


}
