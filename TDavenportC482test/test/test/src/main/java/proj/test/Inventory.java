package proj.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


 /**
 * Inventory Class
 * @author TDavenport
 */

public class Inventory {

/////////////////////////////////////////////////Part Functions///////////////////////////////////////////////////////

     /**
      * Creates Observable array list to store All Parts within
      */
     private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

     /**
      * Adds a newly created part to the All Parts array list
      * @param newPart
      */
    public static void addPart(Part newPart){
        allParts.add(newPart);

    }

     /**
      * Returns all the parts in the "All Parts" observable list.
      * @return allParts returns the "All Parts" observable list.
      */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

     /**
      * Function to look up a specific part via ID number of part searched.
      * @param partID
      * @return tempPart Returns all matching parts with that set of numbers contained in ID field
      */
    public static Part lookupPart(int partID){
        Part tempPart = null;

        for (Part part : allParts) {
            if (part.getId() == partID) {
                tempPart = part;
                return tempPart;
            }
        }
        return null;
    }

     /**
      * Function to look up a specific part via Name of part searched.
      * @param partName
      * @return tempPart Returns all matching parts with that set of characters contained in Name field
      */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> tempPart = FXCollections.observableArrayList();

        for (Part part: allParts){
            if (part.getName().contains(partName)){
                tempPart.add(part);
            }
        }
        return tempPart;
    }

     /**
      * Function to update a part within the all parts observable array list referencing the array index of item.
      * @param index
      * @param selectedPart
      */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);

    }

     /**
      * Function to delete part within the all parts observable array list.
      * @param selectedPart
      */
    public static boolean deletePart(Part selectedPart){

        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }




/////////////////////////////////////////////////Product Functions//////////////////////////////////////////////////////
     /**
      *Creates Observable array list to store All Products within
      */
    static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

     /**
      *Adds a newly created product to the "All Products" array list
      * @param newProduct
      */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);

    }

     /**
      *Returns all the products in the "All Products" observable list.
      * @return tempProduct Returns all matching Products.
      */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

     /**
      * Function to lookup product from all Products
      * @param productID
      * @return tempProduct
      */
        public static Product lookupProduct(int productID) {
            Product tempProduct = null;

            for (Product product : allProducts) {
                if (product.getId() == productID) {
                    tempProduct = product;
                    return tempProduct;
                }
            }
            return null;
        }


     /**
      *Function to look up a specific product via Name of product searched.
      * @param productName
      * @return tempProduct Returns all matching products with that set of characters contained in Name field
      */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> tempProduct = FXCollections.observableArrayList();

        for (Product product: allProducts){
            if (product.getName().contains(productName)){
                tempProduct.add(product);
            }
        }
        return tempProduct;
    }

     /**
      *Function to update a product within the all products observable array list referencing the array index of item.
      * @param index
      * @param selectedProduct
      */
    public static void updateProduct(int index, Product selectedProduct){

        allProducts.set(index, selectedProduct);

    }

     /**
      *Function to delete a part within the all parts observable array list.
      * @param selectedProduct
      */
    public static boolean deleteProduct(Product selectedProduct){

        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }




}
