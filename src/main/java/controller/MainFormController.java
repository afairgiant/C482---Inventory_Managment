package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inhouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private Button exitBtn;
    @FXML
    private Button partAddBtn;
    @FXML
    private Button partDeleteBtn;
    @FXML
    private Button partModifyBtn;
    @FXML
    private TableView<Part> mainPartsTable;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Part, Inhouse> partIdCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TextField partSearch;
    @FXML
    private Button productAddBtn;
    @FXML
    private Button productDeleteBtn;
    @FXML
    private TableView<Product> mainProductTable;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private Button productModifyBtn;
    @FXML
    private TextField productSearch;

    /**
     * //TODO - Update javadoc
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populates Parts Table with allParts
        mainPartsTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //Populates Product Table with allProducts
        mainProductTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }
    public Part selectedPart (int id){
        for (Part part : Inventory.getAllParts()) {
            if(part.getId() == id)
                return part;
        }
        return null;
    }
    /**
     * //TODO - Update javadoc
     * @param event
     * Future Enhancement: Check if the part is associated to any product
     */
    @FXML
    void actionDeletePart(ActionEvent event) {
        System.out.println("Log - Delete Part Button Clicked");
        //TODO - Add Deletion confirmation
        try {
            String selectedPart = mainPartsTable.getSelectionModel().getSelectedItem().getName();

            Inventory.deletePart(mainPartsTable.getSelectionModel().getSelectedItem());

            System.out.println(selectedPart + " was deleted.");
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e);
            System.out.println("Log - No Part Selected");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part from the table to delete");
            alert.show();
        }

    }

    /**
     * //TODO - Update javadoc
     * @param event
     */
    @FXML
    void actionDeleteProduct(ActionEvent event) {
        System.out.println("Log - Delete Product Button Clicked");
        //TODO - Add Deletion confirmation
        //TODO - update with a check if product has associated parts. if yes throw error
        try {
            String selectedProduct = mainProductTable.getSelectionModel().getSelectedItem().getName();

            Inventory.deleteProduct(mainProductTable.getSelectionModel().getSelectedItem());

            System.out.println(selectedProduct + " was deleted.");
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product from the table to delete");
            alert.show();
        }

    }
    /**
     * Load Add Part Menu
     * @param event calls Add Part Menu
     * @throws IOException if AddPart.fxml cannot be found
     */
    @FXML
    void actionDisplayAddPart(ActionEvent event) throws IOException {
        System.out.println("Log - Add Part Button Clicked");

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Load Add Product Menu
     * @param event loads Add Product menu page
     * @throws IOException if AddProduct.fxml cannot be found
     */
    @FXML
    void actionDisplayAddProduct(ActionEvent event) throws IOException {
        System.out.println("Log - Add Product Button Clicked");

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * //TODO - Update javadoc
     * @param event
     * @throws IOException if no part is selected
     */
    @FXML
    void actionDisplayModPart(ActionEvent event) throws IOException {
        System.out.println("Log - Modify Part Button Click");

       try {
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/ModifyPart.fxml"));
           loader.load();

           ModifyPartController MPController = loader.getController();
           MPController.sendPart(mainPartsTable.getSelectionModel().getSelectedIndex(), mainPartsTable.getSelectionModel().getSelectedItem());

           System.out.println("Modifying " + mainPartsTable.getSelectionModel().getSelectedItem().getName());

           stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
           Parent scene = loader.getRoot();
           stage.setScene(new Scene(scene));
           stage.show();
           //Throw error if no Part is selected
       } catch (NullPointerException e){
           System.out.println("Exception: " + e);
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("Selection Error");
           alert.setContentText("Please select a part to modify.");
           alert.show();
       }
    }

    /**
     * //TODO - Update javadoc
     * @param event
     * @throws IOException if no product is selected
     */
    @FXML
    void actionDisplayModProduct(ActionEvent event) throws IOException {
        //DEBUG
        //Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
        System.out.println("Log - Modify Product Button Click");

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(mainProductTable.getSelectionModel().getSelectedIndex(), mainProductTable.getSelectionModel().getSelectedItem());
            //DEBUG
            System.out.println(mainProductTable.getSelectionModel().getSelectedItem().getName());

            stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

/*      ---Changed so would pull selected part data into modify part menu---
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show()*/

        //Error if no product is selected.
        } catch (NullPointerException e){
            System.out.println("Exception: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Please select a product to modify.");
            alert.show();
        }
    }

    /**
     * Exit Button
     * @param event On button click exit the program
     */
    @FXML
    void actionExit(ActionEvent event) {
        System.out.println("Log - Exit Button Click");

        System.exit(0);
    }

    /**
     * Search Part list for matching ID
     * @param id
     */
    /*
    public boolean search(int id){
       for (Part part : Inventory.getAllParts()){
            if(part.getId() == id)
                return true;
       }
       return false;
    }

   public boolean updatePart(int id, Part part){
        int index = -1;
        for(Part partId : Inventory.getAllParts()){
            index++;
            if(partId.getId() == id){
                Inventory.getAllParts().set(index, part);
                return true;
            }
        }
        return false;
    }


    public boolean deletePart(int id){
        for(Part partId : Inventory.getAllParts()){
            if(partId.getId() == id)
                return Inventory.getAllParts().remove(partId);
        }
        return false;
    }
     */

    /**
     * Part Search
     * Makes a observableList called result based off the lookupPart method
     * @param event search parts list
     */
    @FXML
    void actionPartSearch(ActionEvent event) {
        //TODO - make it load default table if nothing is found.
        //Pulls the information from the search text box
        String searchText = partSearch.getText();
        //Debug
            System.out.println(partSearch.getText() + " was entered to search.");
        //Runs the lookupPart method from Inventory for partName
        ObservableList<Part> result = Inventory.lookupPart(searchText);
        try {
            while (result.size() == 0) {
                int partId = Integer.parseInt((searchText));
                result.add((Inventory.lookupPartId(partId)));
            }
            //If result contains null, don't update parts table
            if(!result.contains(null))
                mainPartsTable.setItems(result);
            if(result.contains(null))
                mainPartsTable.setItems(Inventory.getAllParts());

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Parts Found");
            alert.show();
        }

    }

    /**
     * //TODO - Update javadoc
     * @param event search products list
     */
    @FXML
    void actionProductSearch(ActionEvent event) {
        //Pulls the information from the search box
        String searchText = productSearch.getText();
            System.out.println(productSearch.getText() + " was entered to search.");
        ObservableList<Product> result = Inventory.lookupProduct(searchText);
        try{
            while (result.size() == 0){
                int productId = Integer.parseInt((searchText));
                result.add((Inventory.lookupProduct(productId)));
            }
            //If result doesn't contain null, update products table with result
            if(!result.contains(null)) {
                mainProductTable.setItems(result);
            }
            //If there is null, reset mainProductTable to default
            else {
                mainProductTable.setItems(Inventory.getAllProducts());
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Product Found");
            alert.show();
        }
    }


}
