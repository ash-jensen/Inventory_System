package model;

import javafx.collections.ObservableList;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * This class makes Product objects that include getters and setters for id, name, price, stock, min, max, and any associated parts.
 * @author Ashley Jensen
 */
public class Product {
    /**
     * ObservableList of Parts named associatedParts used to hold parts associated with a product.
     * */
    private ObservableList<Part> associatedParts = observableArrayList();
    /**
     * Variable used to hold a product id.
     */
    private int id;
    /**
     * Variable used to hold the name of a product.
     */
    private String name;
    /**
     * Variable used to hold the price of a product.
     */
    private double price;
    /**
     * Variable used to hold the stock amount of a product.
     */
    private int stock;
    /**
     * Variable used to hold the minimum stock amount of stock a product.
     */
    private int min;
    /**
     * Variable used to hold the maximum amount of stock of a product.
     */
    private int max;

    /**
     * Product object constructor with id, name, stock, price, min, max.
     * @param id assigns id
     * @param name assigns name
     * @param stock assigns stock
     * @param price assigns price
     * @param min assigns min
     * @param max assigns max
     */
    public Product(int id, String name, int stock, double price, int min, int max) {
        // Product object constructor variables assigned
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    /**
     * Product object constructor with id, name, stock, price, min, max, and associatedParts.
     @param id assigns id
      * @param name assigns name
     * @param stock assigns stock
     * @param price assigns price
     * @param min assigns min
     * @param max assigns max
     * @param associatedParts assigns associated parts list
     */
    public Product(int id, String name, int stock, double price, int min, int max, ObservableList<Part> associatedParts) {
        // Product constructor variables assigned
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
        this.associatedParts = associatedParts;
    }

    /**
     * This method sets the id variable to id parameter.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method sets the name variable to name parameter.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method sets the price variable to price parameter.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method sets the stock variable to stock parameter.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method sets the min variable to min parameter.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method sets the max variable to max parameter.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method returns id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * This method returns name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns price.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method returns stock.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method returns min.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * This method returns max.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * This method adds a part to associatedPart.
     * @param part is the part to be added to associatedParts list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * This method deletes a part from associatedPart.
     * @param selectedAssociatedPart is the part to be deleted
     * @return returns true if deleted, false if not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
       return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method returns associatedParts.
     * @return returns ObservableList of associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
