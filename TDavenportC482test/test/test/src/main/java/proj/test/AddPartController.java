package proj.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;


/**
 * Add Part Controller
 * @author TDavenport
 */
public class AddPartController implements Initializable {

    public ToggleGroup radioToggle;
    @FXML
    private TextField APFlexField;
    @FXML
    private Label APFlexLabel;
    @FXML
    private TextField APIDField;
    @FXML
    private RadioButton APInHouseRadio;
    @FXML
    private TextField APInvField;
    @FXML
    private TextField APMaxField;
    @FXML
    private TextField APMinField;
    @FXML
    private TextField APNameField;
    @FXML
    private RadioButton APOutsourcedRadio;
    @FXML
    private TextField APPriceField;
    @FXML
    private Button AddPartCancelButton;
    @FXML
    private Button AddPartSaveButton;


    int id = 0;
    int minRandom = 1;
    int maxRandom = 99;


    /**
     * The cancel button within the Add Part Form, when clicked will send the user back to Main Form Screen.
     * @param event Handles Mouse Click event from Cancel Button
     * @throws IOException Throws IOException
     */
    public void OnAddPartCancelButtonClicked (MouseEvent event) throws IOException {

        Stage stage = (Stage)  AddPartCancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
        stage.setTitle("C482 TDavenport Inventory Management System");
        stage.setScene(new Scene(root));
        stage.show();

    }

    /**
     * This sets the Add Part Flexible Label to be changed when user toggles radio button.
     * @param event Handles Mouse Click event from Radio Button
     */
    public void radioToggleFunction(ActionEvent event){

        if(APOutsourcedRadio.isSelected()) {
                APFlexLabel.setText("Company Name");
            }
        else APFlexLabel.setText("Machine ID");
        }


    /**
     * Initialized upon loading
     * Creates a random number to be used as the ID variable, sets the ID field to that unique variable.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Generate a random number to add to Java's Math.random to make ID more Unique
        Random random = new Random();
        int randomNumber = random.nextInt((maxRandom - minRandom) + 1) + minRandom;

        //Auto Set ID field to random number on Add Part screen launch
        int randomIDAutoFill = (int)(Math.random() * (69 + 11) + randomNumber);
        id = randomIDAutoFill;
        APIDField.setText(String.valueOf(randomIDAutoFill));
    }


    /**
     * The save button within the Add Part Form, when clicked will save the inputted data and then send the user back to Main Form Screen.
     * Performs logic check first to warn user of incorrect input.
     * Checks which Radio button is selected and allocates data to correct InHouse or Outsourced.
     * @param event Handles Mouse Click event from Cancel Button
     * @throws IOException Throws IOException
     */
    public void OnAddPartSaveButtonClicked(ActionEvent event) throws IOException{

        try {
            int id = Integer.parseInt(APIDField.getText());
            int stock = Integer.parseInt(APInvField.getText());
            int max = Integer.parseInt(APMaxField.getText());
            int min = Integer.parseInt(APMinField.getText());
            String name = APNameField.getText();
            double price = Double.parseDouble(APPriceField.getText());

            if ( stock < min || stock > max || max < min ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Error");
                alert.setContentText("Please Confirm: Min, Max, and Inventory values must be accurate before saving!");
                alert.showAndWait();
            }

            if (APInHouseRadio.isSelected()){
                int machineID = Integer.parseInt(APFlexField.getText());

                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));

                Stage stage = (Stage)  AddPartCancelButton.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
                stage.setTitle("C482 TDavenport Inventory Management System");
                stage.setScene(new Scene(root));
                stage.show();
            }

            else if (APOutsourcedRadio.isSelected()) {
                String companyName = APFlexField.getText();

                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));

                Stage stage = (Stage)  AddPartCancelButton.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
                stage.setTitle("C482 TDavenport Inventory Management System");
                stage.setScene(new Scene(root));
                stage.show();
            }

            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Error");
                alert.setContentText("Please Confirm: You must select a radio button above!");
                alert.showAndWait();
            }

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please Confirm: Data Incorrect, Check All Values Entered");
            alert.showAndWait();
        }

    }



}
