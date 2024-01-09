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
import java.util.ResourceBundle;


/**
 * Modify Part Controller Class
 * @author TDavenport
 */
public class ModPartController implements Initializable {

    public ToggleGroup radioToggle;
    @FXML
    private TextField MPFlexField;
    @FXML
    private Label MPFlexLabel;
    @FXML
    private TextField MPIDField;
    @FXML
    private RadioButton MPInHouseRadio;
    @FXML
    private TextField MPInvField;
    @FXML
    private TextField MPMaxField;
    @FXML
    private TextField MPMinField;
    @FXML
    private TextField MPNameField;
    @FXML
    private RadioButton MPOutsourcedRadio;
    @FXML
    private TextField MPPriceField;
    @FXML
    private Button ModPartCancelButton;
    @FXML
    private Button ModPartSaveButton;


    private int baseIndex = 0;


    /**
     * Loads the selected part into the Modify Part window.
     * Sets the text fields to the data imported in.
     * @param currentIndex index of part currently selected
     * @param part part imported
     */
    public void loadPart (int currentIndex, Part part){

        baseIndex = currentIndex;

        if (part instanceof InHouse){
            MPInHouseRadio.setSelected(true);
            MPFlexField.setText(String.valueOf(((InHouse)part).getMachineID()));
        }
        else {
            MPOutsourcedRadio.setSelected(true);
            MPFlexField.setText(String.valueOf(((Outsourced)part).getCompanyName()));
        }

        MPIDField.setText(String.valueOf(part.getId()));
        MPNameField.setText(String.valueOf(part.getName()));
        MPInvField.setText(String.valueOf(part.getStock()));
        MPPriceField.setText(String.valueOf(part.getPrice()));
        MPMaxField.setText(String.valueOf(part.getMax()));
        MPMinField.setText(String.valueOf(part.getMin()));

    }


    /**
     * When the Cancel button is clicked the user will be directed back to the Main Screen.
     * @param event
     * @throws IOException
     */
    public void OnModPartCancelButtonClicked (MouseEvent event) throws IOException {

        Stage stage = (Stage)  ModPartCancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
        stage.setTitle("C482 TDavenport Inventory Management System");
        stage.setScene(new Scene(root));

    }

    /**
     * When the Radio button is changed, the flexible label is changed to reflect which button is selected.
     * @param event
     */
    public void radioToggleFunction(ActionEvent event){
        if(MPOutsourcedRadio.isSelected()) {
            MPFlexLabel.setText("Company Name");
        }
        else MPFlexLabel.setText("Machine ID");
    }


    /**
     * When the Save button is pressed it will save the info of the modified Part.
     * It will check to make sure data meets logic checks, and check which radio button is selected, and then correctly
     * save it to either Outsourced or InHouse parts.
     * It will then send the user back to the Main Screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void OnModPartSaveButtonClicked(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(MPIDField.getText());
            int stock = Integer.parseInt(MPInvField.getText());
            int max = Integer.parseInt(MPMaxField.getText());
            int min = Integer.parseInt(MPMinField.getText());
            String name = MPNameField.getText();
            double price = Double.parseDouble(MPPriceField.getText());

            if ( stock < min || stock > max || max < min ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Error");
                alert.setContentText("Please Confirm: Min, Max, and Inventory values must be accurate before saving!");
                alert.showAndWait();
            }

            if (MPInHouseRadio.isSelected()){
                int machineID = Integer.parseInt(MPFlexField.getText());

                InHouse newPart = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.updatePart(baseIndex, newPart);

            }
            else if (MPOutsourcedRadio.isSelected()) {
                String companyName = MPFlexField.getText();

                Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(baseIndex, newPart);

            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Error");
                alert.setContentText("Please Confirm: You must select a radio button above!");
                alert.showAndWait();
            }

            Stage stage = (Stage)  ModPartSaveButton.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
            stage.setTitle("C482 TDavenport Inventory Management System");
            stage.setScene(new Scene(root));

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please Confirm: Data Incorrect, Check All Values Entered");
            alert.showAndWait();
        }

    }


    /**
     * Initialize function, loaded when Mod Part is initialized.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
