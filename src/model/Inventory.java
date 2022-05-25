package model;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * This class makes Inventory objects which holds both Parts and Product.
 * It includes methods to add/delete/update parts and products, lookup parts and products by name and id,
 * get all parts and products, make part and product id's, and input value checks to run before adding to inventory.
 * @author Ashley Jensen
 */
public class Inventory {
    /**
     * ObservableList of Parts named allParts used to hold parts.
     * */
    private static ObservableList<Part> allParts = observableArrayList();
    /**
     * ObservableList of Products named allProducts used to hold products.
     * */
    private static ObservableList<Product> allProducts = observableArrayList();
    /**
     * Variable used to hold part id's for unique creation.
     */
    private static int partID = 1;
    /**
     * Variable used to hold product id's for unique creation.
     */
    private static int productID = 2;


    /** This method adds a part to the allParts inventory list.
     * @param newPart is a new InHouse or Outsourced Part object to add to allParts
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** This method adds a product to the allProducts inventory list.
     * @param newProduct is a new Product object to add to allParts
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**This method searches allParts for a part by part ID.
     * @param partId is used to search allParts for a part by id number
     * @return returns the part with matching id
     */
    public static Part lookupPart(int partId) {
        for (Part currPart : allParts) {
            if (currPart.getId() == partId) {
                return currPart;
            }
        }
        return null;
    }

    /** This method searches allProduct for a product with a specific product ID.
     * @param productId is used to search allProducts for a product by ID number
     * @return returns the product with matching id
     */
    public static Product lookupProduct(int productId) {
        for (Product currProd : allProducts) {
            if (currProd.getId() == productId) {
                return currProd;
            }
        }
        return null;
    }

    /** This method searches allParts for a part by name.
     * @param partName is used to search allParts for a part by name
     * @return ObservableList of all parts found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedParts = observableArrayList();

        for (Part currPart : allParts) {
            if (currPart.getName().contains(partName)) {
                namedParts.add(currPart);
            }
        }
        return namedParts;
    }

    /** This method searches allProducts for a product by name.
     * @param productName is used to search allProducts for a product by name
     * @return returns ObservableList of all products found
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProds = observableArrayList();

        for (Product currProd : allProducts) {
            if (currProd.getName().contains(productName)) {
                namedProds.add(currProd);
            }
        }
        return namedProds;
    }

    /** This method updates a part by removing the old one by index and inserts the new part to allParts.
     * @param index is the index of a part to be removed from allParts
     * @param selectedPart is the new part to insert into allParts
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.remove(index);
        addPart(selectedPart);
    }

    /** This method updates a product by removing the old one by index and inserts the new product to allProducts.
     * @param index is the index of a product to be removed from allProducts
     * @param newProduct is the new product to be inserted into allProducts
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.remove(index);
        addProduct(newProduct);
    }

    /** This method deletes a part from the allParts list.
     * @param selectedPart is the part to be deleted
     * @return returns true if part is deleted, false if not
     */
    public static boolean deletePart(Part selectedPart) {
        return Inventory.getAllParts().remove(selectedPart);
    }

    /** This method deletes a product from the allProduct list.
     * @param selectedProduct is the product to be deleted
     * @return returns true if product is deleted, false if not
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return Inventory.getAllProducts().remove(selectedProduct);
    }

    /** This method returns the ObservableList AllParts.
     * @return returns allParts ObservableList
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** This method returns the ObservableList allProducts.
     * @return returns allProducts ObservableList
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** This method makes and returns a unique partID.
     * @return returns number to be used as unique partID
     */
    public static int makePartId() {
        int tempNum;
        if (partID == 1) {
            tempNum = partID;
            partID += 2;
            return tempNum;
        } else {
            tempNum = partID;
            partID += 2;
            return tempNum;
        }
    }

    /** This method makes and returns unique product ID.
     * @return returns number to be used as unique prodID
     */
    public static int makeProdId() {
        int tempNum;
        if (productID == 2) {
            tempNum = productID;
            productID += 2;
            return tempNum;
        } else {
            tempNum = productID;
            productID += 2;
            return tempNum;
        }
    }

    /** This method displays an error message box if the incorrect input is entered in a text-field that wants an int.
     * @param thisField is the name of the field to display in the error message
     */
    public static void numCheckError(String thisField) {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Field Type Error");
        alert.setContentText("You must enter a number in the " + thisField + " field.");
        alert.showAndWait();
        return;
    }

    /** This method displays an error message box if the incorrect input is entered in text-field that wants a double.
     * @param thisField is the name of the field to display in the error message
     */
    public static void doubleCheckError(String thisField) {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Field Type Error");
        alert.setContentText("You must enter a double in the " + thisField + " field.");
        alert.showAndWait();
        return;
    }
}
