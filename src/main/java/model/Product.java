package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Product {

    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public int id;
    public String name;
    public double price;
    public int stock;
    public int min;
    public int max;

    /**
     * Product Constructor
     * @param id ID number of the product
     * @param name Name of the product
     * @param price Price of the product
     * @param stock Current amount in inventory
     * @param min Minimum stock for the product
     * @param max Maximum stock for the product
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * //TODO - Add Javadoc
     * @return associatedParts to the product
     */
    public ObservableList<Part> getAllAssociatedParts(int id) {
        return associatedParts;
    }

    /**
     * //TODO - Add Javadoc
     * @param part adds selected part to associatedParts list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     *
     * @param part
     */
    public static void removeAssociatedPart(Part part) {
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Part Selected from Associated Parts to remove");
            alert.show();
            return;

        } else if (associatedParts.contains(part)) {
            associatedParts.remove(part);
        }

    }
}
