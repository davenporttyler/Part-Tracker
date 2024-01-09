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
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Add Product Controller Class
 * @author TDavenport
 */
public class AddProdController implements Initializable {

    public TextField AddProdIDField;
    public TextField AddProdMinField;
    public TextField AddProdMaxField;
    public TextField AddProdPriceField;
    public TextField AddProdStockField;
    public TextField AddProdNameField;
    public TextField AddProdSearchField;
    //All Parts (Top) Table
    public TableView<Part> AddProdTopTable;
    public TableColumn<Part, Integer> AddProdPartIDCol1;
    public TableColumn<Part, String> AddProdPartNameCol1;
    public TableColumn<Part, Integer> AddProdInvCol1;
    public TableColumn<Part, Double> AddProdPriceCol1;
    //Associated Parts (Bottom) Table
    public TableView<Part> AddProdAssociatedTable;
    public TableColumn<Part, Integer> AddProdPartIDCol2;
    public TableColumn<Part, String> AddProdPartNameCol2;
    public TableColumn<Part, Integer> AddProdInvCol2;
    public TableColumn<Part, Double> AddProdPriceCol2;
    @FXML
    private Button AddProdAddPartButton;
    @FXML
    private Button AddProdCancelButton;
    @FXML
    private Button AddProdRemAssocButton;
    @FXML
    private Button AddProdSaveButton;
    @FXML
    private Button AddProdSearchButton;

    /**
     * Sets Variable Min and Max to create random number to randomize Product ID.
     */
    int id = 0;
    int minRandom = 111;
    int maxRandom = 9999;

    /**
     * Creates the Temporary Associated Part observable array list.
     */
    private ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();


    /**
     * Cancel Button will close the Add Product window and take user back to Main Form Window.
     * @param event
     * @throws IOException
     */
    public void OnAddProdCancelButtonClicked (MouseEvent event) throws IOException {

        Stage stage = (Stage)  AddProdCancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
        stage.setTitle("C482 TDavenport Inventory Management System");
        stage.setScene(new Scene(root));

    }

    /**
     * The Save button will take the new product and save it, adding it to the Main screen Product table.
     * Captures the values entered by the user.
     * Does a logic check to make sure data is correct.
     * Adds the Associated parts list to that product.
     * @param event
     * @throws IOException
     */
    @FXML public void OnAddProdSaveButtonClicked (ActionEvent event) throws IOException {

        int id = Integer.parseInt(AddProdIDField.getText());
        int stock = Integer.parseInt(AddProdStockField.getText());
        int max = Integer.parseInt(AddProdMaxField.getText());
        int min = Integer.parseInt(AddProdMinField.getText());
        String name = AddProdNameField.getText();
        double price = Double.parseDouble(AddProdPriceField.getText());

        if ( stock < min || stock > max || max < min ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Error");
            alert.setContentText("Please Confirm: Min, Max, and Inventory values must be accurate before saving!");
            alert.showAndWait();
            return;
        }

        Product newproduct = new Product(id, stock, min, max, price, name);

        for (Part part: tempAssociatedParts){

                newproduct.addAssociatedPart(part);
        }

        Inventory.addProduct(newproduct);

        Stage stage = (Stage)  AddProdSaveButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormView.fxml")));
        stage.setTitle("C482 TDavenport Inventory Management System");
        stage.setScene(new Scene(root));

    }

    /**
     * Function to Search the table (Top) to see if searched values match any available parts.
     * Captures text entered, creates a list of parts to add matching parts to.
     * If no search results are found, it will display a warning to the user.
     * Sets the Search field back to empty which will display all results.
     * @param event
     */
    @FXML public void OnAddProdSearchButtonClicked (ActionEvent event) {

        String q = AddProdSearchField.getText();

        ObservableList<Part> partsSearched = addProductPartSearch(q);

        AddProdTopTable.setItems(partsSearched);
        AddProdSearchField.setText("");

        if (partsSearched.size() == 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("No search results found!");
            alert.showAndWait();
        }

    }


    /**
     * Function to Search the table (Top) to see if searched values match any available parts.
     * @param tempPart
     * @return searchedPart Returns a list of all parts matching the search parameters.
     */
    private ObservableList<Part> addProductPartSearch (String tempPart) {

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
     * Function for button to add a part from the top table, to the associated parts table.
     * Performs a check to verify you have selected a part, and also that the part has not already been added.
     * It then adds the part to the associated parts list and the table view.
     * @param event
     */
    @FXML public void OnAddProdAddPartButtonClicked(ActionEvent event){

        Part addPartProduct = AddProdTopTable.getSelectionModel().getSelectedItem();

        if (AddProdTopTable.getSelectionModel().getSelectedItem() == null){

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
            return;

        }
        else {

            tempAssociatedParts.add(addPartProduct);
            AddProdAssociatedTable.setItems(tempAssociatedParts);

            AddProdPartIDCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
            AddProdPartNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
            AddProdInvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
            AddProdPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));

        }



    }

    /**
     * Function to remove an associated part from the bottom table.
     * Then it removes the associated part from the associated parts array list and refreshes the table.
     * @param event
     */
    @FXML public void OnAddProdRemovePartButtonClicked(ActionEvent event){

        Part selectedAssociatedPartRemove = AddProdAssociatedTable.getSelectionModel().getSelectedItem();

        if (AddProdAssociatedTable.getSelectionModel().getSelectedItem() == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please Confirm: You must select a Part before removing!");
            alert.showAndWait();

        }
        else {

            //Product.deleteAssociatedPart(selectedAssociatedPartRemove);
            tempAssociatedParts.remove(selectedAssociatedPartRemove);
            AddProdAssociatedTable.setItems(tempAssociatedParts);
        }



    }


    /**
     * Initialized upon loading Add Product Form. Generates random Product ID and auto sets field to that number.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Generate a random number to add to Java's Math.random to make ID more likely to be Unique
        Random random = new Random();
        int randomNumber = random.nextInt((maxRandom - minRandom) + 1) + minRandom;

        //Auto Set ID field to random number on Add Part screen launch
        int randomIDAutoFill = (int)(Math.random() * (690 + 111) + randomNumber + 333);
        id = randomIDAutoFill;
        AddProdIDField.setText(String.valueOf(randomIDAutoFill));

        //Sets Cell value factory / Property value factory
        AddProdTopTable.setItems(Inventory.getAllParts());
        AddProdPartIDCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdPartNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));


//        AddProdAssociatedTable.setItems(associatedParts);
//        AddProdPartIDCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
//        AddProdPartNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
//        AddProdInvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        AddProdPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));



    }
}
