package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;
import static model.Inventory.*;

/**
 * This class creates AddProductForm. You use this to add a product to inventory list allProducts.
 * @author Ashley Jensen
 */
public class AddProductForm implements Initializable {
    public TextField AProdFIdField;
    public TextField AProdFNameField;
    public TextField AProdFInvField;
    public TextField AProdFPriceField;
    public TextField AProdFMaxField;
    public TextField AProdFMinField;
    public TableView AProdFPartTable;
    public TableColumn AProdFPartTPartID;
    public TableColumn AProdFPartTPartName;
    public TableColumn AProdFPartTInvLevel;
    public TableColumn AProdFPartTPrice;
    public TableColumn AProdFPartTPartIDAdd;
    public TableColumn AProdFPartTPartNameAdd;
    public TableColumn AProdFPartTInvLevelAdd;
    public TableColumn AProdFPartTPriceAdd;
    public TextField AProdFSearchPartField;
    public TableView AProdFAssociatedPartsTable;
    private ObservableList<Part> associatedParts = observableArrayList();

    /**
     * This method initializes AddProductForm.
     * @param url is the location
     * @param resourceBundle is the resources used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // Populate Add Product Form Part Table
        setAProdFPartTable();
    }

    /**
     * This method sets the top part table to all inventory.
     */
    private void setAProdFPartTable() {
        AProdFPartTable.setItems(Inventory.getAllParts());
        AProdFPartTPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AProdFPartTPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AProdFPartTInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AProdFPartTPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method cancels adding a product and returns the user to MainForm on confirmation, nothing is saved.
     * @param actionEvent is the Cancel button click
     * @throws IOException if stage unable to change scene
     */
    public void AProdFCancelButtonAction(ActionEvent actionEvent) throws IOException {
        // Return to Main Form on Cancel Action
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 390);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method adds a new product to allProducts list after checking for correct input in text-fields, then returns to MainForm.
     * @param actionEvent on Save button click
     * @throws IOException if stage uanble to change scene
     */
    public void AProdFSaveButtonAction(ActionEvent actionEvent) throws IOException {
        Alert alert;
        String thisString;
        int id;
        String name;
        int stock;
        double price;
        int max;
        int min;

        // Get new values from text-fields to update part
        id = Inventory.makeProdId();
        name = AProdFNameField.getText();
        thisString = AProdFPriceField.getText();
        try {
            price = Double.parseDouble(thisString);
        }
        catch (NumberFormatException e){
            doubleCheckError("Price");
            return;
        }
        thisString = AProdFInvField.getText();
        try {
            stock = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e){
            numCheckError("Inv");
            return;
        }
        thisString = AProdFMinField.getText();
        try {
            min = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e){
            numCheckError("Min");
            return;
        }
        thisString = AProdFMaxField.getText();
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
        // Make new product and add to allProducts
        else {
            Product newProd = new Product(id, name, stock, price, min, max, associatedParts);
            Inventory.addProduct(newProd);
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
     * This method searches the part inventory for a part by id or name, otherwise alerts not found.
     * @param actionEvent on enter in search text-field
     */
    public void AProdFSearchPartAction(ActionEvent actionEvent) {
        // Get text from search box
        String toSearch = AProdFSearchPartField.getText();

        // Search part list for Part Name
        ObservableList<Part> searchedParts = Inventory.lookupPart(toSearch);

        // if string match not found, try changing to int and search for Part ID
        if(searchedParts.size() == 0) {
            try{
                int partId = Integer.parseInt(toSearch);
                Part foundPart = Inventory.lookupPart(partId);
                if (foundPart != null) {
                    searchedParts.add(foundPart);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }

        // If searched item not found, alert user and reload table
        if (searchedParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found");
            alert.setContentText("Part not found.");
            alert.showAndWait();

            // Populate Parts Table
            setAProdFPartTable();
            return;
        }

        // Set part table to display list of matching parts
        AProdFPartTable.setItems(searchedParts);
    }

    /**
     * This method associates a part with a product and puts it into associated parts table.
     * @param actionEvent on Add button click
     */
    public void AProdFAddButtonAction(ActionEvent actionEvent) {
        Alert alert;

        //Get selected part from table
        Part selected = (Part) AProdFPartTable.getSelectionModel().getSelectedItem();

        // If nothing selected, alert user to select part
        if (selected == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please select a part to add to this product");
            alert.showAndWait();
            return;
        }

        // Add part to list
        associatedParts.add(selected);

        // Populate Add Product Form Associated Parts Table
        AProdFAssociatedPartsTable.setItems(associatedParts);
        AProdFPartTPartIDAdd.setCellValueFactory(new PropertyValueFactory<>("id"));
        AProdFPartTPartNameAdd.setCellValueFactory(new PropertyValueFactory<>("name"));
        AProdFPartTInvLevelAdd.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AProdFPartTPriceAdd.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method removes an associated part from a product and removes it from associated parts table.
     * @param actionEvent on Remove Associated Part button click
     */
    public void AProdFRemovePartAction(ActionEvent actionEvent) {
        Alert alert;

        // Get selected part from table
        Part selected = (Part)AProdFAssociatedPartsTable.getSelectionModel().getSelectedItem();

        // If nothing selected, alert user to select part
        if (selected == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please select a part to remove from this product.");
            alert.showAndWait();
            return;
        }

        // Confirm user wants to delete product & delete
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part from this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            if (!associatedParts.remove(selected)) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Delete unsuccessful.");
                alert.showAndWait();
            }
        }
    }

}
