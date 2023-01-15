package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inhouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * FXML id selectors
     */
    @FXML
    private Button cancelBtn;

    @FXML
    private ToggleGroup manufacturerGroup;

    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private Label manufacturerLabel;

    @FXML
    private TextField manufacturerTxt;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private Button savePartBtn;


    /**
     * //TODO
     * Save New Part
     * @param event Saves the New Part
     * @throws IOException when main menu can't be found
     */
    @FXML
    public void actionSavePart(ActionEvent event) throws IOException {

        //TODO - Min, max and stock checks. after that passes, do the radiobutton checks then add the part
        try {
            //TODO - a loop for adding part ID, here or in inventory class?
            //TODO - loop that takes the largest ID number from allParts list and adds +1
            //FUTURE ENHANCEMENT : Check to make sure no fields are empty, and throw an error based on each field. Fill error with variable based off the check?
            int newId = 0;
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partInvTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());

            int machineId;
            String companyName;

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
            //Generate New ID for part once all checks pass.
            newId = Inventory.newPartId();

            //If the In-house RBtn is selected add the part to Inventory as In-house
            if (inhouseRBtn.isSelected()){
                machineId = Integer.parseInt(manufacturerTxt.getText());
                Part addPart = new Inhouse(newId, name, price, stock, min, max, machineId);
                Inventory.addPart(addPart);

            //If the outsourcedRBtn is selected, add the part to Inventory as Outsourced
            }
            if (outsourcedRBtn.isSelected()){
                companyName = manufacturerTxt.getText();
                Part addPart = new Outsourced(newId, name, price, stock, min, max, companyName);
                Inventory.addPart(addPart);
            }

            //Load MainForm after save
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        //If all else fails, throw an error.
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
            return;
        }
    }
    /**
     * InHouse Radio Btn
     *  @param event Sets label to "Machine ID"
     */
    @FXML
    public void actionLoadInHouse(ActionEvent event) {
        manufacturerLabel.setText("Machine ID");
    }
    /**
     * Outsourced Radio Btn
     * @param event sets label to "Company Name"
     */
    @FXML
    public void actionLoadOutsourced(ActionEvent event) {
        manufacturerLabel.setText("Company Name");
    }
    /**
     * @param event closes addPart view and goes back to main menu
     * @throws IOException if MainForm.fxml isn't found
     */
    @FXML
    public void actionDisplayMain(ActionEvent event) throws IOException {
        System.out.println("Log - Add Part: Clicked cancel button");

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}