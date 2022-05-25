package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.*;

/**
 * This class creates AddPartForm. You use this to add a part to inventory list allParts.
 * @author Ashley Jensen
 */
public class AddPartForm implements Initializable {
    public RadioButton InHouseRadioButton;
    public RadioButton OutsourcedRadioButton;
    public Label MachIdToCompName;
    public TextField APartFIdField;
    public TextField APartFNameField;
    public TextField APartFInvField;
    public TextField APartFPriceField;
    public TextField APartFMaxField;
    public TextField APartFMinField;
    public TextField APartFMachIdField;

    /**
     * This method initializes AddPartForm.
     * @param url is the location
     * @param resourceBundle is the resources used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * This method cancels adding a part and returns user to MainForm.
     * @param actionEvent on Cancel button click
     * @throws IOException if stage unable to change scenes
     */
    public void APartFCancelButtonAction(ActionEvent actionEvent) throws IOException {
        // Return to Main Form on Cancel Action
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 390);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method saves a new part to allParts inventory list after checking for correct input in text-fields, then returns to MainForm.
     * @param actionEvent on Save button click
     * @throws IOException if stage unable to change scenes
     */
    public void APartFSaveButtonAction(ActionEvent actionEvent) throws IOException {
        Alert alert;
        String thisString;
        int id;
        String name;
        double price;
        int stock;
        int min;
        int max;
        int machineId;
        String companyName;

        // Get new, valid values from text-fields to update part
        id = Inventory.makePartId();
        name = APartFNameField.getText();
        thisString = APartFPriceField.getText();
        try {
            price = Double.parseDouble(thisString);
        }
        catch (NumberFormatException e){
            doubleCheckError("Price");
            return;
        }
        thisString = APartFInvField.getText();
        try {
            stock = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e){
            numCheckError("Inv");
            return;
        }
        thisString = APartFMinField.getText();
        try {
            min = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e){
            numCheckError("Min");
            return;
        }
        thisString = APartFMaxField.getText();
        try {
            max = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e){
            numCheckError("Max");
            return;
        }
        // Min less than Max check
        if (min > max) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Min/Max Error");
            alert.setContentText("Min must be less than max. Please enter a valid number.");
            alert.showAndWait();
            return;
        }
        // Stock less than Max check
        else if ((stock < min) || (stock > max)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inv Error");
            alert.setContentText("Inv cannot be less than min or greater than max. Please enter a valid number.");
            alert.showAndWait();
            return;
        }
        // Check if InHouse or Outsourced part and add to allParts list
        else {
            if (InHouseRadioButton.isSelected()) {
                thisString = APartFMachIdField.getText();
                try {
                    machineId = Integer.parseInt(thisString);
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(newPart);
                }
                catch (NumberFormatException e){
                    numCheckError("Machine ID");
                    return;
                }
            }
            else {
                companyName = APartFMachIdField.getText();
                Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(newPart);
            }
        }

        // Return to Main Form
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 390);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method changes the label to match the radio button selection: In-House => MachineID.
     * @param actionEvent In-House radio button selected
     */
    public void APartFInHouseRadioButtonAction(ActionEvent actionEvent) {
        // Set label text to match radio button selection
        MachIdToCompName.setText("Machine ID");
    }

    /**
     * This method changes the label to match the radio button selection: Outsourced => CompanyName.
     * @param actionEvent Outsourced radio button selected
     */
    public void APartFOutsourcedRadioButtonAction(ActionEvent actionEvent) {
        // Set label text to match radio button selection
        MachIdToCompName.setText("Company Name");
    }
}



