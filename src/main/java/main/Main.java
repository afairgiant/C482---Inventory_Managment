package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * @author Alex Fair
 * Main Class
 */

public class Main extends Application {
    /**
     * Start Method:
     * @param stage gets MainForm.fxml at start of program
     * @throws IOException if MainForm.fxml is not found.
     */
    @Override
    public void start (Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main Method
     * @param args
     * //TODO update javaDoc
     */
    public static void main (String[] args){
        /*
          Create sample data
         */

        //Products
        Product giantbike = new Product(1000, "Giant Bike", 999.99, 5, 1, 100);
        Product unicycle = new Product(1001, "unicycle", 69.99, 3, 1, 10);

        //Parts - InHouse
        Part brakes = new Inhouse(1, "Brakes", 12.99, 15, 1, 20, 111);

        //Parts - Outsourced
        Part rim = new Outsourced(2, "Rim", 56.99, 15, 1, 20, "Mega Bikes Inc");

        /*
          Load sample data
         */
        Inventory.addProduct(giantbike);
            System.out.println(giantbike.getName() + " was loaded.");
        Inventory.addProduct(unicycle);
        System.out.println(unicycle.getName() + " was loaded.");
        Inventory.addPart(brakes);
            System.out.println(brakes.getName() + " was loaded.");
        Inventory.addPart(rim);
            System.out.println(rim.getName() + " was loaded.");

        System.out.println("\nTEST DATA HAS BEEN LOADED.");

        launch();
    }


}
