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

public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private Button addAvailPartBtn;

    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;

    @FXML
    private TableColumn<Part, Integer> assocPartInvCol;

    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;

    @FXML
    private TableView<Part> assocPartTable;

    @FXML
    private TableColumn<Part, Integer> availPartIdCol;

    @FXML
    private TableColumn<Part, Integer> availPartInvCol;

    @FXML
    private TableColumn<Part, String> availPartNameCol;

    @FXML
    private TableColumn<Part, Double> availPartPriceCol;

    @FXML
    private TableView<Part> availPartTable;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField productIdTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private Button removeAssocPartBtn;

    @FXML
    private Button saveProductBtn;

    @FXML
    private TextField searchPartTxt;
    //Temporary Parts list until the new product is saved
    private ObservableList<Part> assocPartsListTemp = FXCollections.observableArrayList();

    /**
     * //TODO
     * @param event
     */
    @FXML
    void actionAssociatePart(ActionEvent event) {
        //Gets selected item from the table
        Part selectedPart = availPartTable.getSelectionModel().getSelectedItem();

        //Error if no part is selected
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Please select an associated part to remove");
            alert.show();
            return;
        } if (!assocPartsListTemp.contains(selectedPart)) {
            assocPartsListTemp.add(selectedPart);
            assocPartTable.setItems(assocPartsListTemp);
            
        }
    }

    /**
     * //TODO
     * @param event
     * @throws IOException
     */
    @FXML
    void actionDisplayMain(ActionEvent event) throws IOException {
        System.out.println("Log - Cancel Button Clicked");

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Remove Associated Part from Product
     * @param event removes associated part from list for current product
     */
    @FXML
    void actionRemoveAssocPart(ActionEvent event) {
        Part selectedPart = assocPartTable.getSelectionModel().getSelectedItem();

        //Error if no part is selected
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Please select an associated part to remove");
            alert.show();
            return;
        } else if (assocPartsListTemp.contains(selectedPart)) {
                assocPartsListTemp.remove(selectedPart);
                assocPartTable.setItems(assocPartsListTemp);
        }
    }

    /**
     * Save the new Product
     * @param event Saves the new Product
     * @throws IOException when main menu can't be found
     */
    @FXML
    void actionSaveProduct(ActionEvent event) throws IOException {
    //TODO - Fix the associatedPart List
        try {
            int newId = 0;
            newId = Inventory.newProductId();
            String name = productNameTxt.getText();
            int stock = Integer.parseInt(productInvTxt.getText());
            double price = Double.parseDouble(productPriceTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());

            //Checking if min is less than max, if not give an error.
            if (min >= max) {
                System.out.println("Min/Max Check Error");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Input Error");
                alert.setContentText("Min must be less then Max. \n Please try again.");
                alert.show();
                return;
            }
            //If min is less than max, check that stock is between min and max.
            //FUTURE ENHANCEMENT : Break down the check into 2 separate ones and throw 2 different errors for each check.
            else if (stock < min || stock > max){
                System.out.println("Stock Check Error");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Input Error");
                alert.setContentText("Inventory cannot be LESS than Min or GREATER than Max");
                alert.show();
                return;
            }

            //Add the new product to the allProducts list
            Product addProduct = new Product(newId, name, price, stock, min, max);
            Inventory.addProduct(addProduct);
            for (Part part : assocPartsListTemp) {
                if (part != assocPartsListTemp) addProduct.addAssociatedPart(part);
                System.out.println(addProduct.getAllAssociatedParts(newId).size());
            }

            System.out.println("Log: " + name + " was added to the products list.");

            //Load Main Menu after save is completed
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }catch (NumberFormatException e) {
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
            return;
        }
    }


    /**
     * //TODO - call a method instead of recoding?
     * @param event
     */
    @FXML
    void actionSearchParts(ActionEvent event) {

    }

    /**
     * //TODO
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //All Parts - Top Table
        availPartTable.setItems(Inventory.getAllParts());
        availPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        //TODO - DEBUG: does not load the correct parts
        //Associated Parts - Bottom Table
        assocPartTable.setItems(assocPartsListTemp);
        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
