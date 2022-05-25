package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

import static model.Inventory.deletePart;
import static model.Inventory.deleteProduct;

/**
 * This class creates MainForm. You can view and delete inventory of both parts and products from here.
 * @author Ashley Jensen
 */
public class MainForm implements Initializable {
    public TableView MFPartTable;
    public TableView MFProdTable;
    public TableColumn MFPartTPartID;
    public TableColumn MFPartTPartName;
    public TableColumn MFPartTInvLevel;
    public TableColumn MFPartTPrice;
    public TableColumn MFProdTProductID;
    public TableColumn MFProdTProductName;
    public TableColumn MFProdTInvLevel;
    public TableColumn MFProdTPrice;
    public Button MFDeletePartButton;
    public Button MFDeleteProdButton;
    public TextField MFSearchPartField;
    public TextField MFSearchProdField;

    /**
     * This method initializes MainForm.
     * @param url is the location
     * @param resourceBundle is the resources used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate Main Form Part Table
        populatePartsTable();

        // Populate Main Form Product Table
        populateProductsTable();
    }

    /**
     * This method takes you to addPartForm.
     * @param actionEvent on Add button click in Parts table
     * @throws IOException  if the stage is unable to change scene
     */
    public void MFAddPartAction(ActionEvent actionEvent) throws IOException {
        // Load Add Part Form
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 340);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method takes you to modifyPartForm.
     * @param actionEvent on Modify button click in Parts table
     * @throws IOException if the stage is unable to change scene
     */
    public void MFModifyPartAction(ActionEvent actionEvent) throws IOException {
        // Get selected item to modify
        Part toPass = (Part)MFPartTable.getSelectionModel().getSelectedItem();

        // If nothing selected, notify user to select item
        if (toPass == null) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part to Modify Error");
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
            return;
        }

        // Pass item to Modify Part Form
        ModifyPartForm.passPart(toPass);

        // Load Modify Part Form
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 340);
        stage.setTitle("Modify Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * RUNTIME ERROR: A logic error that I worked on was one in which the delete button in the table was not showing that a "delete" had
     * occurred if you tried removing from a searched list. The part would remain in the table in the searched list, which gave the
     * appearance that the delete button was not working, even on confirmation. This was fixed by first deleting the part (or product in
     * the product table), then reloading inventory in its entirety to the table to show that the part (or product) was no longer present
     * in inventory.
     *
     * This method confirms a part is selected, confirms delete and deletes the part from the allParts inventory list.
     * @param actionEvent on Delete button in Parts table
     */
    public void MFDeletePartAction(ActionEvent actionEvent) {
        Alert alert;

        // Get selected part from table
        Part selected = (Part)MFPartTable.getSelectionModel().getSelectedItem();

        // If nothing selected, alert user to select part
        if (selected == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please select a part to delete.");
            alert.showAndWait();
            return;
        }

        // Confirm user wants to delete part & delete
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            if (!deletePart(selected)) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Delete unsuccessful.");
                alert.showAndWait();
            }
        }

        // Populate updated Main Form Part Table
        populatePartsTable();

    }

    /**
     * This method takes you to addPartForm.
     * @param actionEvent on Add button click in Products table
     * @throws IOException if the stage is unable to change scene
     */
    public void MFAddProductAction(ActionEvent actionEvent) throws IOException {
        // Load Add Product Form
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 500);
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method takes you to modifyProductForm.
     * @param actionEvent on Modify button click in Products table
     * @throws IOException if the stage is unable to change scene
     */
    public void MFModifyProductAction(ActionEvent actionEvent) throws IOException {
        // Get selected item to modify
        Product toPass = (Product)MFProdTable.getSelectionModel().getSelectedItem();

        // If nothing selected, notify user to select item
        if (toPass == null) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product to Modify Error");
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
            return;
        }

        // Pass item to Modify Product Form
        ModifyProductForm.passProduct(toPass);

        // Load Modify Product Form
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 500);
        stage.setTitle("Modify Product Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method confirms a product is selected, confirms delete and deletes the product from allProducts inventory list.
     * @param actionEvent on Delete button in product table
     */
    public void MFDeleteProductAction(ActionEvent actionEvent) {
        Alert alert;

        // Get selected product from table
        Product selected = (Product)MFProdTable.getSelectionModel().getSelectedItem();

        // If nothing selected, alert user to select product
        if (selected == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
            return;
        }

        // Check if product has associated parts
        if (!associatedPartCheck(selected)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("You cannot delete a product with associated parts.");
            alert.showAndWait();
            return;
        }

        // Confirm user wants to delete product & delete
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            if (!deleteProduct(selected)) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Delete unsuccessful.");
                alert.showAndWait();
            }
        }

        // Populate updated Main Form Product Table
        populateProductsTable();
    }

    /**
     * This method checks if a product's associated list is empty, if yes returns true.
     * @param selected the selected product to check for associated parts
     * @return  returns true if empty, false if not
     */
    private boolean associatedPartCheck(Product selected) {
        // Returns true if product does not have associated parts
        return selected.getAllAssociatedParts().isEmpty();
    }

    /**
     * This method Exits the program if the user confirms this is what they want to do.
     * @param actionEvent on Exit button
     */
    public void MFExitAction(ActionEvent actionEvent) {
        Alert alert;

        // Confirm user wants to exit program
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            // Close program
            Platform.exit();
        }
    }

    /**
     * This method gets input from search box in Parts table to find a part by id or name, otherwise alerts not found.
     * @param actionEvent on enter in search text-field
     */
    public void MFSearchPartAction(ActionEvent actionEvent) {
        // Get text from search box
        String toSearch = MFSearchPartField.getText();

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

            // Populate Main Form Part Table
            populatePartsTable();
            return;
        }

        // Set part table to display list of matching parts
        MFPartTable.setItems(searchedParts);
    }

    /**
     * This method gets input from search bos in Product table to find a product by id or name, otherwise alerts not found.
     * @param actionEvent on enter in search text-field
     */
    public void MFSearchProdAction(ActionEvent actionEvent) {
        // Get text from search box
        String toSearch = MFSearchProdField.getText();

        // Search product list for Product Name
        ObservableList<Product> searchedProds = Inventory.lookupProduct(toSearch);

        // if string match not found, try changing to int and search for Product ID
        if(searchedProds.size() == 0) {
            try{
                int prodId = Integer.parseInt(toSearch);
                Product foundProd = Inventory.lookupProduct(prodId);
                if (foundProd != null) {
                    searchedProds.add(foundProd);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }

        // If searched item not found, alert user and reload table
        if (searchedProds.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found");
            alert.setContentText("Product not found.");
            alert.showAndWait();

            // Populate Main Form Product Table
            populateProductsTable();
            return;
        }

        // Set product table to display list of matching products
        MFProdTable.setItems(searchedProds);
    }

    /**
     * This method populates the Parts table on MainForm.
     */
    private void populatePartsTable() {
        // Populate Main Form Part Table
        MFPartTable.setItems(Inventory.getAllParts());
        MFPartTPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MFPartTPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MFPartTInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MFPartTPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method populates the Products table on MainForm.
     */
    private void populateProductsTable() {
        // Populate Main Form Product Table
        MFProdTable.setItems(Inventory.getAllProducts());
        MFProdTProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MFProdTProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MFProdTInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MFProdTPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
