package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inhouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;

public class ModifyPartController {

    Stage stage;
    Parent scene;

    @FXML
    private Button cancelBtn;

    @FXML
    private RadioButton inhouseRBtn;
    @FXML
    private ToggleGroup manufacturerGroup;
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

    int currentIndex = 0;

    /**
     * Sends the part selected in Main Menu
     *
     * @param part the selected part from the main menu
     */
    @FXML
    public void sendPart(int selectedIndex, Part part){
        currentIndex = selectedIndex;
        //Fill basic info of selected part
        partIdTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(String.valueOf(part.getName()));
        partPriceTxt.setText(String.valueOf(part.getPrice()));
        partInvTxt.setText(String.valueOf(part.getStock()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        partMaxTxt.setText(String.valueOf(part.getMax()));

        //Check if In-house and set accordingly
        if (part instanceof Inhouse){
            inhouseRBtn.setSelected(true);
            manufacturerLabel.setText("Machine ID");
            manufacturerTxt.setText(String.valueOf(((Inhouse) part).getMachineId()));
        }
        //Check if Outsourced and set accordingly
        if (part instanceof Outsourced){
            outsourcedRBtn.setSelected(true);
            manufacturerLabel.setText("Company Name");
            manufacturerTxt.setText(((Outsourced) part).getCompanyName());
        }

    }



    /**
     * Cancel Button
     * @param event loads Main Menu
     * @throws IOException when MainForm.fxml cannot be found
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
     * InHouse Radio Button
     * @param event Sets label to "Machine ID"
     */
    @FXML
    void actionLoadInHouse(ActionEvent event) {
        manufacturerLabel.setText("Machine ID");
    }

    /**
     * Outsourced Radio Button
     * @param event Sets Label to "Company Name"
     */
    @FXML
    void actionLoadOutsourced(ActionEvent event) {
        manufacturerLabel.setText("Company Name");
    }

    /**
     * //TODO
     * Save Modified Part
     * @param event Saves the modified Part
     * @throws IOException when main menu can't be found
     */
    @FXML
    void actionSavePart(ActionEvent event) throws IOException{
        /**
         * @RuntimeError - it's overwriting top line of the parts table on main when saved. Fixed by passing the selectedIndex to variable currentIndex during sendPart. Updated the updatePart method to use an int
         */
        System.out.println("Log - Save Button Clicked");
        try {
            //TODO - a loop for adding part ID, here or in inventory class?
            //TODO - loop that takes the largest ID number from allParts list and adds +1
            //FUTURE ENHANCEMENT : Check to make sure no fields are empty, and throw an error based on each field. Fill error with variable based off the check?
            int id = Integer.parseInt(partIdTxt.getText());
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
            /**
             * @RuntimeError : not checking if machineId or companyName match the correct type, int vs string.
             */
            //If the In-house RBtn is selected add the part to Inventory as In-house
            if (inhouseRBtn.isSelected()){
                machineId = Integer.parseInt(manufacturerTxt.getText());
                Part updatedPart = new Inhouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(currentIndex, updatedPart);

                //If the outsourcedRBtn is selected, add the part to Inventory as Outsourced
            }
            if (outsourcedRBtn.isSelected()){
                companyName = manufacturerTxt.getText();
                Part updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(currentIndex, updatedPart);
            }

            //Load MainForm after save
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            //If all else fails, throw an error.
            //TODO - make an error if there is only in company Name or letters in MachineID
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
                         +Machine ID: NUMBERS ONLY. \s
                         +Company Name: LETTERS ONLY. \s
                        """);
            alert.show();
            return;
        }

    }

}
