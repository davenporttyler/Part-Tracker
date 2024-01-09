package proj.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {


    private int id;
    private int stock;
    private String name;
    private int min;
    private int max;
    private double price;


    /**
     * Associated Parts observable list
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Constructor for Product
     * @param id id of product
     * @param stock stock of product
     * @param min minimum product allowable
     * @param max maximum product allowable
     * @param price price of product
     * @param name name of product
     */
    public Product(int id, int stock, int min, int max, double price, String name){
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.price = price;
        this.name = name;
    }

    /**
     * Function to add a part to the Associated Parts observable list
     * @param part
     */
    public void addAssociatedPart(Part part){

        associatedParts.add(part);

    }

    /**
     * Function to delete a selected associated part from the Associated Parts observable list.
     * @param selectedAssociatedPart selected associated part
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Function to get all Associated Parts from the observable list.
     * @return Associated Parts observable list
     */
    public ObservableList<Part> getAllAssociatedParts(){

        return associatedParts;

    }


    /**
     * Getter for ID
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * Setter for ID
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Getter for Stock
     * @return stock
     */
    public int getStock(){
        return stock;
    }

    /**
     * Setter for Stock
     * @param stock
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Getter for Name
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Setter for name
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Getter for price
     * @return price
     */
    public double getPrice(){
        return price;
    }

    /**
     * Setter for Price
     * @param price
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Getter for Min
     * @return min
     */
    public int getMin(){
        return min;
    }

    /**
     * Setter for Min
     * @param min
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * Getter for Max
     * @return max
     */
    public int getMax(){
        return max;
    }

    /**
     * Setter for Max
     * @param max
     */
    public void setMax(int max){
        this.max = max;
    }





}
