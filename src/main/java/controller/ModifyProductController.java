/**
 * ModifyProduct.fxml Controller Class
 */

package controller;

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
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML // fx:id="addAvailPartBtn"
    private Button addAvailPartBtn;

    @FXML // fx:id="assocPartIdCol"
    private TableColumn<Part, Integer> assocPartIdCol;

    @FXML // fx:id="assocPartInvCol"
    private TableColumn<Part, Integer> assocPartInvCol;

    @FXML // fx:id="assocPartNameCol"
    private TableColumn<Part, String> assocPartNameCol;

    @FXML // fx:id="assocPartPriceCol"
    private TableColumn<Part, Double> assocPartPriceCol;

    @FXML // fx:id="assocPartTable"
    private TableView<Part> assocPartTable;

    @FXML // fx:id="availPartIdCol"
    private TableColumn<Part, Integer> availPartIdCol;

    @FXML // fx:id="availPartInvCol"
    private TableColumn<Part, Integer> availPartInvCol;

    @FXML // fx:id="availPartNameCol"
    private TableColumn<Part, String> availPartNameCol;

    @FXML // fx:id="availPartPriceCol"
    private TableColumn<Part, Double> availPartPriceCol;

    @FXML // fx:id="availPartTable"
    private TableView<Part> availPartTable;

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn;

    @FXML // fx:id="productIdTxt"
    private TextField productIdTxt;

    @FXML // fx:id="productInvTxt"
    private TextField productInvTxt;

    @FXML // fx:id="productMaxTxt"
    private TextField productMaxTxt;

    @FXML // fx:id="productMinTxt"
    private TextField productMinTxt;

    @FXML // fx:id="productNameTxt"
    private TextField productNameTxt;

    @FXML // fx:id="productPriceTxt"
    private TextField productPriceTxt;

    @FXML // fx:id="removeAssocPartBtn"
    private Button removeAssocPartBtn;

    @FXML // fx:id="saveProductBtn"
    private Button saveProductBtn;

    @FXML // fx:id="searchPartTxt"
    private TextField partSearch;
    private int currentIndex = 0;
    //Temporary Parts list until the new product is saved
    private ObservableList<Part> assocPartsListTemp = FXCollections.observableArrayList();

    /**
     * //TODO - Setup Javadoc
     * @param product
     */

    @FXML
    public void sendProduct (int selectedIndex, Product product){
        currentIndex = selectedIndex;

        productIdTxt.setText(String.valueOf(product.getId()));
        productNameTxt.setText(String.valueOf(product.getName()));
        productPriceTxt.setText(String.valueOf(product.getPrice()));
        productInvTxt.setText(String.valueOf(product.getStock()));
        productMinTxt.setText(String.valueOf(product.getMin()));
        productMaxTxt.setText(String.valueOf(product.getMax()));

        assocPartTable.setItems(product.getAllAssociatedParts(product.getId()));
        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Put the associated Parts in a temp list while fixing
        //ERROR - will load product without any associated parts but not after modifying it and adding one. - Fixed

        product.getAllAssociatedParts(product.getId());
        System.out.println(Product.associatedParts.size());
        //Product.associatedParts.addAll(product.getAllAssociatedParts());

    }
    @FXML
    public void actionAssociatePart(ActionEvent event) {
        Part selectedPart = availPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Part Selected.");
            alert.show();
            return;
            } else if (!Product.associatedParts.contains(selectedPart)){
            Product.associatedParts.add(selectedPart);
            //TODO - Setup an error saying the part is already associated.
            //Debug
            System.out.println(selectedPart.getName()+ " was associated.");
            System.out.println(Product.associatedParts.size());
            assocPartTable.setItems(Product.associatedParts);
        }
    }

    /**
     * //TODO - Setup Javadoc
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionDisplayMain(ActionEvent event) throws IOException {
        System.out.println("Log - Cancel Button Clicked");

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * //TODO - Setup Javadoc
     * @param event
     */
    @FXML
    public void actionRemoveAssocPart(ActionEvent event) {
        Part selectedPart = assocPartTable.getSelectionModel().getSelectedItem();
        Product.removeAssociatedPart(selectedPart);
        assocPartTable.setItems(Product.associatedParts);
    }

    @FXML
    public void actionSaveProduct(ActionEvent event) throws IOException {

       try {
            /*DEBUG
          It is re-adding the associated part to the list on save.
         */

            int id = Integer.parseInt(productIdTxt.getText());
            String name = productNameTxt.getText();
            int stock = Integer.parseInt(productInvTxt.getText());
            double price = Double.parseDouble(productPriceTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());

            //TODO - Add if statements for errors based on miss entered information

            Product updatedProduct = new Product(id, name, price, stock, min , max);
            if(!updatedProduct.equals(Product.associatedParts)){
                Inventory.updateProduct(currentIndex, updatedProduct);
                //Debugging
                System.out.println(updatedProduct.getName() + " was updated.\n");
            }

            //TODO - note explaining save system once working.

            for (Part part: Product.associatedParts){
                if(!Product.associatedParts.contains(part))
                    updatedProduct.addAssociatedPart(part);
            }

            //FUTURE ENHANCEMENT : Call a save script instead of copying code?.
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
       } catch (NumberFormatException e) {
           System.out.println("Exception: " + e);
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("Invalid Entry");
           alert.setContentText("""
                   One of the fields you entered was invalid.\s
                    +Name: Accepts Letters, Numbers and Symbols.\s
                    +Inventory: NUMBERS ONLY.\s
                    +Min: NUMBERS ONLY.\s
                    +Max: NUMBERS ONLY.\s
                   """);
           alert.show();
       }

    }

    /**
     * //TODO - Setup Javadoc
     * @param event
     */
    @FXML
    void actionSearchParts(ActionEvent event) {
        //TODO - Make search for modify product
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
                availPartTable.setItems(result);
            if(result.contains(null))
                availPartTable.setItems(Inventory.getAllParts());

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Parts Found");
            alert.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //All Parts Table - top table
        availPartTable.setItems(Inventory.getAllParts());
        availPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        /**
         * Error - Wasn't loading ID's correctly, had a typo.
         */
           //System.out.println(Inventory.getAllParts());
        availPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Associated Parts Table - bottom table.

       /* assocPartTable.setItems(associatedParts);
        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));*/

    }
}
