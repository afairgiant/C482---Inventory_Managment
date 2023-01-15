package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Inventory Class
 *
 */
// TODO - update note

public class Inventory {

    // List of all parts in inventory.
    private static ObservableList<Part> allPartsList = FXCollections.observableArrayList();
    // List of all products in inventory.
    private static ObservableList<Product> allProductsList = FXCollections.observableArrayList();

    /**
     * Gets all parts in inventory.
     * @return all parts in inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allPartsList;
    }

    /**
     * Gets all products in inventory.
     * @return all products in inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProductsList;
    }

    /**
     * Adds a part to inventory.
     * @param newPart part to add
     */
    public static void addPart(Part newPart) {
        allPartsList.add(newPart);
        //TODO - make a partIndex variable, add 1 to the observable list size for each add. makes the ID?
    }

    /**
     * Adds a product to inventory.
     * @param newProduct product to add
     */
    public static void addProduct(Product newProduct) {
        allProductsList.add(newProduct);
        //TODO - make a productIndex variable? add 1 to the observable list size for each add. makes the ID?
    }
    /**
     * Removes a part from inventory.
     * @param selectedPart The part to remove.
     */
    public static void deletePart(Part selectedPart) {
        if (allPartsList.contains(selectedPart)) {
            allPartsList.remove(selectedPart);
        } else {System.out.println("Part not found.");}
    }

    /**
     * //TODO - Check if Works
     * Removes a product from inventory.
     * @param selectedProduct product to remove.
     */
    public static void deleteProduct(Product selectedProduct) {
        if (allProductsList.contains(selectedProduct)){
            allProductsList.remove(selectedProduct);
        } else {System.out.println("Product not found.");}
    }
    /**
     * //TODO - Check if Works
     * Update Part Method
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allPartsList.set(index, selectedPart);
        // TODO - needs update and clarification of index?
    }
    /**
     * //TODO - Check if Works
     * Update Product Method
     * @param index the position where to set the product
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProductsList.set(index, selectedProduct);
        // TODO - needs update and clarification of index?
    }
    /**
     * Lookup Part Method
     * @param partId looks up a part by id.
     * @return null if not found
     */
    public Part selectPart(int partId) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        System.out.println("No Part by ID Found\n");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("No Part Found");
        alert.show();
        return null;
    }
    public static Part lookupPartId(int partId){
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        System.out.println("No Part by ID Found\n");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("No Part Found");
        alert.show();
        return null;
    }

    /**
     * Lookup Part Name Method - makes an observable list of filtered parts
     * @param partName
     * @return filtered parts by name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        for (Part part: allPartsList){
            if (part.getName().contains(partName)) {
                filteredParts.add(part);
            }
        }
        return filteredParts;
    }

    /**
     * //TODO - Update Javadoc
     * @param productId
     * @return
     */

    public static Product lookupProduct(int productId){
        for (Product product : Inventory.getAllProducts()){
            if (product.getId() == productId){
                return product;
            }
        }
        System.out.println("No Product by ID Found.\n");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("No Product Found");
        alert.show();
        return null;
    }
    /**
     * //TODO - Update note
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        for (Product product: allProductsList){
            if (product.getName().contains(productName)){
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
    /*
    public static int idCheck (){
        ObservableList<Part> idCheck = FXCollections.observableArrayList();
        int maxId = 0;
        for (Part part: getAllParts()){
            if (part.getId() > maxId) maxId++;
        }
        System.out.println("Largest Part ID is: " + maxId);
        return maxId;
    }

        public static int newID (){
        int maxId = Inventory.idCheck();
        int newId = maxId + 1;
        System.out.println("The new part ID is: " + newId);
        return newId;
    }

    */

    /**
     * Finds the current largest part ID and adds 1 and generates newPartId
     * @return newPartId
     */
    public static int newPartId (){
        int newId = 0;
        int maxId = 0;
        ObservableList<Part> idCheck = FXCollections.observableArrayList();
        for (Part part: getAllParts()){
            if(part.getId() > maxId) maxId++;
        }
        System.out.println("Largest Part ID is: " + maxId);
        newId = maxId + 1;
        System.out.println("The New part ID is: "+ newId);
        return newId;
    }

    /**
     * Generate New Product ID
     * //TODO Java Doc
     * @return
     */
    public  static int newProductId(){
        int newId = 0;
        int maxId = 1000;
        ObservableList<Product> idcheck = FXCollections.observableArrayList();
        for (Product product: getAllProducts()){
            if (product.getId() > maxId) maxId++;
        }
        //System.out.println("Largest Product ID is: " + maxId);
        newId = maxId + 1;
        System.out.println("The New Product ID is: " + newId);
        return newId;

    }
}