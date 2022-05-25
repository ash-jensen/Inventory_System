package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Javadoc file supplied as separate zipped folder in submission.
 */

/**
* FUTURE ENHANCEMENT: To enhance this system in the future, I would add a ticket form and a customer object.  The form
* would include customer information fields, two uneditable tables (products inventory and parts inventory tables),
* and a third "Ticket" table that would serve to keep track of parts and products added or deleted from a ticket.
* The Ticket table would also add up the total cost of parts and products, and upon checkout would save a new object
* type of customer to a customer array.
*
* This is the Main class for the Inventory Management System application.
* @author Ashley Jensen
*/
public class Main extends Application {
    /** This method sets the stage with the Main Form scene.
     * @param stage the stage to set */
    @Override
    public void start(Stage stage) throws Exception {
        // Set the stage and show scene
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root, 850, 390));
        stage.show();
    }

    /** This is the main method. This is the first method that gets called to run the application.
     * @param args the args to pass */
    public static void main(String[] args) {
        // Launch application
        launch(args);
    }
}
