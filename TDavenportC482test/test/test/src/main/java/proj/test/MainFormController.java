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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;




/**
 * Main Form Controller
 * @author TDavenport
 */
public class MainFormController implements Initializable {

    public TableColumn<Part, Integer> partIDCol;
    //<Part, Integer> <Part, String> <Part, Double>
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partStockCol;
    public TableColumn<Part, Double> partPriceCol;
    public TableColumn<Product, Integer> prodIDCol;
    //<Product, Integer> <Product, String> <Product, Double>
    public TableColumn<Product, String> prodNameCol;
    public TableColumn<Product, Integer> prodStockCol;
    public TableColumn<Product, Double> prodPriceCol;
    public TableView<Part> partTable;
    public TableView<Product> productTable;
    public TextField mainProductsTF;
    public TextField mainPartsTF;
    @FXML
    private Button AddPartButtonMain;
    @FXML
    private Button AddProdButtonMain;
    @FXML
    private Button DelPartButtonMain;
    @FXML
    private Button DelProdButtonMain;
    @FXML
    private Button ExitAppButtonMain;
    @FXML
    private Button ModPartButtonMain;
    @FXML
    private Button ModProdButtonMain;
    @FXML
    private AnchorPane MainForm;


    
    Stage stage;
    Scene scene;
    Parent root;




    /**
     * When the Exit button is clicked the user will receive a confirmation window to accept closing app, or to stay in app.
     * @param event
     */
    public void OnExitAppButtonMainClicked (MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Inventory Management System?");
        alert.setContentText("Press 'OK' to close the Inventory Management System OR 'Cancel' to stay inside.");

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) MainForm.getScene().getWindow();
            System.out.println("You clicked the Exit Button");
            stage.close();
        }
    }

    ////////////////////////////  6 Buttons to manipulate data and views  ////////////////////////////////////////

    /**
     * When the Add Part Button is clicked it will open the Add Part View FXML page and allow user to add a part.
     * @param event
     * @throws IOException
     */
    public void OnAddPartButtonMainClicked (MouseEvent event) throws IOException {

       Stage stage = (Stage) AddPartButtonMain.getScene().getWindow();
       Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPartView.fxml")));
       stage.setTitle("Add Part");
       stage.setScene(new Scene(root));

    }

    /**
     * When the Modify Part button is clicked it verifies that a part has been selected and if not it will warn the user to select a part.
     * Once a part is selected it will open the Modify Part View FXML page and allow the user to modify the selected part.
     * @param event
     * @throws IOException
     */
    public void OnModPartButtonMainClicked (MouseEvent event) throws IOException {

        if (partTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Error");
            alert.setContentText("Please Confirm: You must select a Part to Modify!");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModPartView.fxml"));
            loader.load();

            ModPartController ModPController = loader.getController();
            ModPController.loadPart(partTable.getSelectionModel().getSelectedIndex(),partTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * When the Add Product Button is clicked it will open the Add Product View FXML page and allow user to add a product.
     * @param event
     * @throws IOException
     */
    public void OnAddProdButtonMainClicked (MouseEvent event) throws IOException {

        Stage stage = (Stage) AddProdButtonMain.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProdView.fxml")));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root));

    }

    /**
     * When the Modify Product button is clicked it verifies that a product has been selected and if not it will warn the user to select a product.
     * Once a product is selected it will open the Modify Product View FXML page and allow the user to modify the selected product.
     * @param event
     * @throws IOException
     */
    public void OnModProdButtonMainClicked (MouseEvent event) throws IOException {



        if (productTable.getSelectionModel().getSelectedItem() == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Error");
            alert.setContentText("Please Confirm: You must select a Product to Modify!");
            alert.showAndWait();
        }
        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModProdView.fxml"));
            loader.load();

            ModProdController ModPdController = loader.getController();
            ModPdController.loadProd(productTable.getSelectionModel().getSelectedIndex(),productTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    /**
     * When the Delete Part button is clicked it verifies that a part has been selected and if not it will warn the user to select a part.
     * Once a part is selected it will open the prompt the user to confirm they want to delete the selected part.
     * After the user confirms they want the part to be deleted it will delete it from the part table.
     * @param event
     */
    public void OnDeletePartButtonMainClicked (ActionEvent event) {

        if (partTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please Confirm: You must select a Part before Deleting!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm to Delete");
            alert.setContentText("Please Confirm You Want to Delete Part");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());

            }
        }
    }

    /**
     * When the Delete Product button is clicked it verifies that a product has been selected and if not it will warn the user to select a product.
     * Once a product is selected it will open the prompt the user to confirm they want to delete the selected product.
     * After the user confirms they want the product to be deleted it will delete it from the product table.
     * If the product has associated parts, it will prevent the deletion until associated parts have been removed.
     * @param event
     */
    @FXML public void OnDeleteProdButtonMainClicked (ActionEvent event) {

        Product prodToDelete = productTable.getSelectionModel().getSelectedItem();

        if (prodToDelete == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please Confirm: You must select a Product before Deleting!");
            alert.showAndWait();
        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm to Delete");
            alert.setContentText("Please Confirm: Do you Want to Delete Product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){

                if (prodToDelete.getAllAssociatedParts().size() > 0){

                    Alert alertAssoc = new Alert(Alert.AlertType.CONFIRMATION);
                    alertAssoc.setTitle("Confirm Delete");
                    alertAssoc.setHeaderText("Confirm to Delete");
                    alertAssoc.setContentText("Product has Associated Parts, remove parts before deleting!");
                    alertAssoc.showAndWait();
                    return;

                }
                Inventory.deleteProduct(prodToDelete);
            }
        }
    }

    ////////////////////////////////////  Search buttons and functions  ////////////////////////////////////////////////

    /**
     * When the user presses the Search button for the parts table, it will take the contents of the Parts Search Bar text field.
     * An observable list is created and searched parts and set to table view.
     * If no search results are found, it will display a warning to the user.
     * It then sets the text box back to an empty string, running again will result in all parts being displayed again as normal.
     * @param event
     */
    @FXML public void OnPartSearchButtonMainClicked (ActionEvent event) {

        String q = mainPartsTF.getText();

        ObservableList<Part> partsSearched = searchPartName(q);

        partTable.setItems(partsSearched);
        mainPartsTF.setText("");

        if (partsSearched.size() == 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("No search results found!");
            alert.showAndWait();
        }
    }

    /**
     * Queries the searched Part array list against the allParts array list to show only matching items correlated to search parameters.
     * @param tempPart
     * @return searchedPart List of searched parts matching search criteria.
     */
    private ObservableList<Part> searchPartName (String tempPart) {

        ObservableList<Part> searchedPart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

            for (Part sp : allParts) {
                if (sp.getName().contains(tempPart)) {
                    searchedPart.add(sp);
                }
            }
            return searchedPart;
    }

    /**
     * When the user presses the Search button for the product table, it will take the contents of the Products Search Bar text field.
     * An observable list is created and searched products and set to table view.
     * If no search results are found, it will display a warning to the user.
     * It then sets the text box back to an empty string, running again will result in all products being displayed again as normal.
     * @param event
     */
    @FXML
    public void OnProductSearchButtonMainClicked (ActionEvent event) {

        String q = mainProductsTF.getText();

        ObservableList<Product> productsSearched = searchProductName(q);


        productTable.setItems(productsSearched);
        mainProductsTF.setText("");

        if (productsSearched.size() == 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("No search results found!");
            alert.showAndWait();
        }


    }

    /**
     * Queries the searched Product array list against the allProducts array list to show only matching items correlated to search parameters.
     * @param tempProd
     * @return searchedProduct List of searched products matching search criteria.
     */
    private ObservableList<Product> searchProductName (String tempProd) {

        ObservableList<Product> searchedProduct = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product sp : allProducts) {
            if (sp.getName().contains(tempProd)){
                searchedProduct.add(sp);
            }

        }
        return searchedProduct;
    }


    /////////////////////////////////////  Sample Data  /////////////////////////////////////////////////////

    /**
     * sets variable for first run to default value
     */
    private static boolean firstRun = true;

    /**
     * Checks to verify if it is the first time the program has run since launch.
     * Auto Fills sample part and product data on first launch.
     */
    private void addSampleData(){
        if (!firstRun){
            return;
        }
        firstRun = false;

        //Inventory.addPart(new InHouse(69, "bacon", 4.20, 8, 1, 99, 123));

        Part a = new Outsourced(9, "Bacon", 4.99, 63, 1, 99, "Bacon Hills Pigs");
        Inventory.addPart(a);
        Part b = new Outsourced(8, "Ham", 3.33, 43, 1, 99, "Ham Forest Pigs");
        Inventory.addPart(b);
        Part c = new InHouse(7, "Salami", 8.88, 83, 1, 99, 8484);
        Inventory.addPart(c);
        Part d = new InHouse(6, "Peperoni", 2.22, 73, 1, 99, 6666);
        Inventory.addPart(d);
        Part e = new InHouse(5, "Dough", 1.11, 53, 1, 99, 5555);
        Inventory.addPart(e);
        Part f = new InHouse(4, "Cheese", 4.44, 24, 1, 99, 4444);
        Inventory.addPart(f);
        Part g = new Outsourced(3, "Red Sauce", 0.99, 80, 1, 99, "Saucy Bobs Sauces");
        Inventory.addPart(g);

        Product z = new Product(1199, 1, 1, 99, 9.99, "Peperoni Pizza");
        Inventory.addProduct(z);
        Product y = new Product(1166, 1, 1, 99, 14.99, "Meaty Madness Pizza");
        Inventory.addProduct(y);
    }


    /**
     * Sets column data and cell / property value factories on initialization
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        addSampleData();

        partTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        productTable.setItems(Inventory.getAllProducts());
        prodIDCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        prodStockCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));





    }




}
