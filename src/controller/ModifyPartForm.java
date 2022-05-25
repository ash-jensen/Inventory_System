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
import model.Part;
import static model.Inventory.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class creates ModifyPartForm. You can use this to modify an existing part in allParts.
 * @author Ashley Jensen
 */
public class ModifyPartForm implements Initializable {
    public Label MachIdToCompName;
    public TextField MPartFIdField;
    public TextField MPartFNameField;
    public TextField MPartFInvField;
    public TextField MPartFPriceField;
    public TextField MPartFMaxField;
    public TextField MPartFMinField;
    public TextField MPartFMachIdField;
    private static Part partToMod = null;
    public Label MPartFIdLabel;
    public RadioButton MPartFInHouseRadioButton;
    public RadioButton OutsourcedRadioButton;
    private int id;
    private String name;
    private int stock;
    private double price;
    private int max;
    private int min;
    private int machineId;
    private String companyName;

    /**
     * This method initializes ModifyPartForm.
     * @param url is the location
     * @param resourceBundle is the resources used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set part fields
        setPartFields(partToMod);
    }

    /**
     * This method is used to get the selected part from the MainForm page.
     * @param passedPart is the selected part from MainForm
     */
    public static void passPart(Part passedPart) {
        // Get part from Main Form
        partToMod = passedPart;
    }

    /**
     * This method sets the part fields to the passed in part values.
     * @param partToMod is the part passed in to fill text-fields
     */
    private void setPartFields(Part partToMod) {
        // Set text-fields to passed in part values
        id = partToMod.getId();
        MPartFIdField.setText(Integer.toString(id));
        name = partToMod.getName();
        MPartFNameField.setText(name);
        stock = partToMod.getStock();
        MPartFInvField.setText(Integer.toString(stock));
        price = partToMod.getPrice();
        MPartFPriceField.setText(Double.toString(price));
        max = partToMod.getMax();
        MPartFMaxField.setText(Integer.toString(max));
        min = partToMod.getMin();
        MPartFMinField.setText(Integer.toString(min));
        // Check if InHouse or Outsourced part
        if (partToMod instanceof InHouse) {
            machineId = ((InHouse) partToMod).getMachineId();
            MPartFMachIdField.setText(Integer.toString(machineId));
            MachIdToCompName.setText("Machine ID");
            MPartFInHouseRadioButton.setSelected(true);
        }
        else {
            companyName = ((Outsourced)partToMod).getCompanyName();
            MPartFMachIdField.setText(companyName);
            MachIdToCompName.setText("Company Name");
            OutsourcedRadioButton.setSelected(true);
        }
    }

    /**
     * This method cancels any changes to the part and takes the user back to MainForm
     * @param actionEvent is the Cancel button click
     * @throws IOException if the stage unable to change scenes
     */
    public void MPartFCancelButtonAction(ActionEvent actionEvent) throws IOException {
        // Return to Main Form on Cancel action
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 390);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method saves any modifications to a part into the allParts inventory list.
     * @param actionEvent is the Save button click
     * @throws IOException is thrown if stage unable to change scenes
     */
    public void MPartFSaveButtonAction(ActionEvent actionEvent) throws IOException {
        Alert alert;
        String thisString;

        // Get index of original part to pass to updatePart()
        int indexOfPart = Inventory.getAllParts().indexOf(partToMod);

        // Create new part from input
        name = MPartFNameField.getText();
        thisString = MPartFPriceField.getText();
        try {
            price = Double.parseDouble(thisString);
        }
        catch (NumberFormatException e) {
            doubleCheckError("Price");
            return;
        }
        thisString = MPartFInvField.getText();
        try {
            stock = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e) {
            numCheckError("Inv");
            return;
        }
        thisString = MPartFMinField.getText();
        try {
            min = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e) {
            numCheckError("Min");
            return;
        }
        thisString = MPartFMaxField.getText();
        try {
            max = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e) {
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
        // Check if InHouse or Outsourced part
        else {
            if (MPartFInHouseRadioButton.isSelected()) {
                thisString = MPartFMachIdField.getText();
                try {
                    machineId = Integer.parseInt(thisString);
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(indexOfPart, newPart);
                }
                catch (NumberFormatException e) {
                    numCheckError("Machine ID");
                    return;
                }
            } else {
                companyName = MPartFMachIdField.getText();
                Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(indexOfPart, newPart);
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
    public void MPartFInHouseRadioButtonAction(ActionEvent actionEvent) {
        // Set label text to match radio button selection
        MachIdToCompName.setText("Machine ID");
    }

    /**
     * This method changes the label to match the radio button selection: Outsourced => CompanyName.
     * @param actionEvent Outsourced radio button selected
     */
    public void MPartFOutsourcedRadioButtonAction(ActionEvent actionEvent) {
        // Set label text to match radio button selection
        MachIdToCompName.setText("Company Name");
    }
}
