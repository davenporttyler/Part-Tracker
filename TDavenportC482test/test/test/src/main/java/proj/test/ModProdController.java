package proj.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;



public class ModProdController implements Initializable {

    public TextField ModProdIDField;
    public TextField ModProdNameField;
    public TextField ModProdInvField;
    public TextField ModProdPriceField;
    public TextField ModProdMaxField;
    public TextField ModProdMinField;
    public TableView<Part> ModProdTopTable;
    public TableColumn<Part, Integer> ModProdIDCol1;
    public TableColumn<Part, String> ModProdNameCol1;
    public TableColumn<Part, Integer> ModProdInvCol1;
    public TableColumn<Part, Double> ModProdPriceCol1;
    public TableView<Part> ModProdAssocTable;
    public TableColumn<Part, Integer> ModProdIDCol2;
    public TableColumn<Part, String> ModProdNameCol2;
    public TableColumn<Part, Integer> ModProdInvCol2;
    public TableColumn<Part, Double> ModProdPriceCol2;
    public TextField ModProdSearchField;
    @FXML
    private Button ModProdAddPartButton;
    @FXML
    private Button ModProdCancelButton;
    @FXML
    private Button ModProdRemAssocButton;
    @FXML
    private Button ModProdSaveButton;
    @FXML
    private Button ModProdSearchButton;

    private ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

    private int baseIndex = 0;

    private Product modifyProduct = null;


    /**
     * Function to load the selected Product into the Modify Product window.
     * Sets the fields to the imported data.
     * @param currentIndex index of currently selected product
     * @param product
     */
    public void loadProd (int currentIndex, Product product) {

        //get index of Prod to update
        baseIndex = currentIndex;
        modifyProduct = product;

        //Load in values to fields
        ModProdIDField.setText(String.valueOf(product.getId()));
        ModProdNameField.setText(String.valueOf(product.getName()));
        ModProdInvField.setText(String.valueOf(product.getStock()));
        ModProdPriceField.setText(String.valueOf(product.getPrice()));
        ModProdMaxField.setText(String.valueOf(product.getMax()));
        ModProdMinField.setText(String.valueOf(product.getMin()));

        //Load in Associated Parts array list
        tempAssociatedParts.addAll(product.getAllAssociatedParts());


    }


    /**
     * Function for Search Button inside Modify Product.
     * If no search results are found, it will display a warning to the user.
     * Sets the Search field back to empty which will display all results.
     * @param event
     */
    @FXML public void OnModProdSearchButtonClicked (ActionEvent event) {

        String q = ModProdSearchField.getText();

        ObservableList<Part> partsSearched = modifyProductPartSearch(q);

        ModProdTopTable.setItems(partsSearched);
        ModProdSearchField.setText("");

        if (partsSearched.size() == 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("No search results found!");
            alert.showAndWait();
        }
    }


    /**
     * Function that holds the logic for performing the search in Modify Product.
     * @param tempPart
     * @return
     */
    private ObservableList<Part> modifyProductPartSearch (String tempPart) {

        ObservableList<Part> searchedPart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part sp : allParts) {
            if (sp.getName().contains(tempPart)){
                searchedPart.add(sp);
            }

        }
        return searchedPart;
    }


    /**
     * Function for the Add Part button within the Modify Product window that will put a part from the top table, into
     * the lower table.
     * Performs a check to verify a part is selected, and that a part has not already been added to the lower table.
     * @param event
     */
    @FXML public void OnModProdAddPartButtonClicked(ActionEvent event){

        Part addPartProduct = ModProdTopTable.getSelectionModel().getSelectedItem();

        if (ModProdTopTable.getSelectionModel().getSelectedItem() == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please Confirm: You must select a Part before adding!");
            alert.showAndWait();

        }
        if (tempAssociatedParts.contains(addPartProduct)){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please Confirm: Part has already been added!");
            alert.showAndWait();

        }
        else {

            tempAssociatedParts.add(addPartProduct);
            ModProdAssocTable.setItems(tempAssociatedParts);

        }

    }

    /**
     * Function to remove an associated part from the lower table.
     * Performs a check that a part is selected before removing.
     * @param event
     */
    @FXML public void OnModProdRemovePartButtonClicked(ActionEvent event){

        Part selectedAssociatedPartRemove = ModProdAssocTable.getSelectionModel().getSelectedItem();

        if (ModProdAssocTable.getSelectionModel().getSelectedItem() == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please Confirm: You must select a Part before removing!");
            alert.showAndWait();

        }
        else {

            tempAssociatedParts.remove(selectedAssociatedPartRemove);
            ModProdAssocTable.setItems(tempAssociatedParts);
        }



    }





    /**
     * Function to save a modified product.
     * Performs a logic check to verify the data meets the criteria, then creates the part and updates the associated part list
     * for this product.
     * Returns the user to the Main Screen once saved.
     *
     * @RUNTIME_ERROR I had problems when initially creating the logic for this structure for a few reasons, it was similar to the Mod Part
     * Controller, but differed in a few distinct ways.
     * 1) I forgot a simple "Return" line. It was saving incorrect information rather than returning me to the incorrect
     * data to change it and resolve data type or other issue. Eventually I was able to figure it out via Google.
     * 2) Integrating the ability for a Modified Product to gain and retain child parts was not something I logically could
     * determine the best way to handle initially. I know in one of the videos the options were to either update in place, or
     * clear out the data and recreate a new one. I tried to do everything updated in place and in my mind that is the most logical.
     * However, I was running into issues with the Associated Parts array list always becoming inaccurate, some iterations it was doubling
     * each time saved, or adding last added part 1 additional time. However, with the help of an instructor we settled on creating a temporary
     * list to handle the adds and changes within the window, and saving to the associated parts only once the user clicks the save button.
     *
     * @param event
     * @throws IOException
     */

    @FXML
    public void OnModProdSaveButtonClicked(ActionEvent event) throws IOException {


        int id = Integer.parseInt(ModProdIDField.getText());
        int stock = Integer.parseInt(ModProdInvField.getText());
        int max = Integer.parseInt(ModProdMaxField.getText());
        int min = Integer.parseInt(ModProdMinField.getText());
        String name = ModProdNameField.getText();
        double price = Double.parseDouble(ModProdPriceField.getText());

        if ( stock < min || stock > max || max < min ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Error");
            alert.setContentText("Please Confirm: Min, Max, and Inventory values must be accurate before saving!");
            alert.showAndWait();
            return;
        }

        //Setup constructor for new product to set variable
        Product modifyProduct = new Product(id, stock, min, max, price, name);

        //Clear out associated parts array list before resetting new values
        modifyProduct.getAllAssociatedParts().clear();

        //Updates the values of modified product to new values at current index
        Inventory.updateProduct(baseIndex, modifyProduct);

        //Iterates over the associated parts to resend each to associated parts array list
        for (Part part: tempAssociatedParts){

                modifyProduct.addAssociatedPart(part);
        }


        Stage stage = (Stage)  ModProdSaveButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
        stage.setTitle("C482 TDavenport Inventory Management System");
        stage.setScene(new Scene(root));


    }


    /**
     * Function for Modify Product Cancel button, when clicked the user will be returned to the Main Screen.
     * @param event
     * @throws IOException
     */
    public void OnModProdCancelButtonClicked (MouseEvent event) throws IOException {

        Stage stage = (Stage)  ModProdCancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
        stage.setTitle("C482 TDavenport Inventory Management System");
        stage.setScene(new Scene(root));

    }


    /**
     * Initialize function, loaded when Modify Product is initialized.
     * Sets the top parts table to the all parts list, and sets table view columns.
     * Sets the bottom table to the temporary associated parts list which can then be modified.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ModProdTopTable.setItems(Inventory.getAllParts());

        ModProdIDCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        ModProdAssocTable.setItems(tempAssociatedParts);

        ModProdIDCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdInvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));





    }


}
