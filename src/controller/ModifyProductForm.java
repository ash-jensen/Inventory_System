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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;
import static model.Inventory.*;

/**
 * This class creates ModifyProductForm. You can use this to modify an existing part in allProducts.
 * @author Ashley Jensen
 */
public class ModifyProductForm implements Initializable {
    private static Product prodToMod;
    public TextField MProdFIdField;
    public TextField MProdFNameField;
    public TextField MProdFInvField;
    public TextField MProdFPriceField;
    public TextField MProdFMaxField;
    public TextField MProdFMinField;
    public TableView MProdFPartTable;
    public TableColumn MProdFPartTPartID;
    public TableColumn MProdFPartTPartName;
    public TableColumn MProdFPartTInvLevel;
    public TableColumn MProdFPartTPrice;
    public TableView MProdFAssociatedPartsTable;
    public TableColumn MProdFPartTPartIDAdd;
    public TableColumn MProdFPartTPartNameAdd;
    public TableColumn MProdFPartTInvLevelAdd;
    public TableColumn MProdFPartTPriceAdd;
    public Button MProdFRemovePartButton;
    public TextField MProdFSearchPartField;
    private int id;
    private String name;
    private int stock;
    private double price;
    private int max;
    private int min;
    private ObservableList<Part> associatedParts = observableArrayList();

    /**
     * This method initializes ModifyProductForm.
     * @param url is the location
     * @param resourceBundle is the resources used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the product fields and populate tables
        setProdFields(prodToMod);
        setMProdFPartTable();
        setMProdFAssociatedPartsTable();
    }

    /**
     * This method is used to get the selected product from the MainForm page.
     * @param toPass is the selected product from MainForm
     */
    public static void passProduct(Product toPass) {
        // Get product from Main Form
        prodToMod = toPass;
    }

    /**
     * This method sets the product fields to the passed in product values.
     * @param prodToMod is the product passed in to fill text-fields
     */
    private void setProdFields(Product prodToMod) {
        // Set text-fields to passed in product values
        id = prodToMod.getId();
        MProdFIdField.setText(Integer.toString(id));
        name = prodToMod.getName();
        MProdFNameField.setText(name);
        stock = prodToMod.getStock();
        MProdFInvField.setText(Integer.toString(stock));
        price = prodToMod.getPrice();
        MProdFPriceField.setText(Double.toString(price));
        max = prodToMod.getMax();
        MProdFMaxField.setText(Integer.toString(max));
        min = prodToMod.getMin();
        MProdFMinField.setText(Integer.toString(min));
    }

    /**
     * This method sets the part table to the current inventory in allParts
     */
    private void setMProdFPartTable() {
        // Set parts table to hold allParts list
        MProdFPartTable.setItems(Inventory.getAllParts());
        MProdFPartTPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MProdFPartTPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MProdFPartTInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MProdFPartTPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method sets the associated parts table to any parts currently associated with the product
     */
    private void setMProdFAssociatedPartsTable() {
        // Copy associated parts to new array
        associatedParts = observableArrayList(prodToMod.getAllAssociatedParts());

        // Set associated parts table to show associated parts list
        MProdFAssociatedPartsTable.setItems(associatedParts);
        MProdFPartTPartIDAdd.setCellValueFactory(new PropertyValueFactory<>("id"));
        MProdFPartTPartNameAdd.setCellValueFactory(new PropertyValueFactory<>("name"));
        MProdFPartTInvLevelAdd.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MProdFPartTPriceAdd.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method cancels any changes to the product and takes the user back to MainForm.
     * @param actionEvent is the Cancel button click
     * @throws IOException if stage unable to change scenes
     */
    public void MProdFCancelButtonAction(ActionEvent actionEvent) throws IOException {
        // Return to Main Form on Cancel action
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 390);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method saves any modifications to a product into the allProducts inventory list
     * @param actionEvent is the Save button click
     * @throws IOException is thrown if stage unable to change scenes
     */
    public void MProdFSaveButtonAction(ActionEvent actionEvent) throws IOException {
        Alert alert;
        String thisString;

        // Get index of original product to pass to updateProduct()
        int indexOfProd = Inventory.getAllProducts().indexOf(prodToMod);

        // Create new product from input
        name = MProdFNameField.getText();
        thisString = MProdFPriceField.getText();
        try {
            price = Double.parseDouble(thisString);
        }
        catch (NumberFormatException e){
            doubleCheckError("Price");
            return;
        }
        thisString = MProdFInvField.getText();
        try {
            stock = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e){
            numCheckError("Inv");
            return;
        }
        thisString = MProdFMinField.getText();
        try {
            min = Integer.parseInt(thisString);
        }
        catch (NumberFormatException e){
            numCheckError("Min");
            return;
        }
        thisString = MProdFMaxField.getText();
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
            Inventory.updateProduct(indexOfProd, newProd);
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
     * This method adds a part from inventory to be associated with a product
     * @param actionEvent is the Add button being clicked
     */
    public void MProdFAddButtonAction(ActionEvent actionEvent) {
        Alert alert;

        //Get selected part from table
        Part selected = (Part) MProdFPartTable.getSelectionModel().getSelectedItem();

        // If nothing selected, alert user to select part
        if (selected == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please select a part to add to this product");
            alert.showAndWait();
            return;
        }

        // Add part to current product part list
        associatedParts.add(selected);

        // Populate Add Product Form Associated Parts Table
        MProdFAssociatedPartsTable.setItems(associatedParts);
        MProdFPartTPartIDAdd.setCellValueFactory(new PropertyValueFactory<>("id"));
        MProdFPartTPartNameAdd.setCellValueFactory(new PropertyValueFactory<>("name"));
        MProdFPartTInvLevelAdd.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MProdFPartTPriceAdd.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method deletes an associated part from a product.
     * @param actionEvent is the Remove Associated Part button being clicked
     */
    public void MFDeletePartAction(ActionEvent actionEvent) {
        Alert alert;

        // Get selected part from table
        Part selected = (Part)MProdFAssociatedPartsTable.getSelectionModel().getSelectedItem();

        // If nothing selected, alert user to select part
        if (selected == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please select a part to remove from this product.");
            alert.showAndWait();
            return;
        }

        // Confirm user wants to delete part & delete
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

    /**
     * This method searches for a part in the inventory table by Part Name or Part ID, error if not found
     * @param actionEvent on enter in search text-field
     */
    public void MProdFSearchPartAction(ActionEvent actionEvent) {
        // Get text from search box
        String toSearch = MProdFSearchPartField.getText();

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
            setMProdFPartTable();
            return;
        }

        // Set part table to display list of matching parts
        MProdFPartTable.setItems(searchedParts);
    }
}
