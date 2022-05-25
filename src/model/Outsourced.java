package model;

/**
 * This class makes Outsourced objects that extend Part and include getters/setters for id, name, min, max, and company name.
 * @author Ashley Jensen
 */
public class Outsourced extends Part {
    /**
     * Variable used to hold the company name of an Outsourced part.
     */
    private String companyName;

    /**
     * Outsource part constructor with id, name, price, stock, min, max, and companyName
     * @param id assigns id
     * @param name assigns name
     * @param price assigns price
     * @param stock assigns stock
     * @param min assigns min
     * @param max assigns max
     * @param companyName assigns companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        // Part superclass constructor
        super(id, name, price, stock, min, max);

        // companyName variable assigned
        this.companyName = companyName;
    }

    /**
     * This method sets the companyName variable to companyName parameter.
     * @param companyName name to be added to the Outsourced part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method returns companyName.
     * @return returns the company name associated with the OutsourcedPart
     */
    public String getCompanyName() {
        return companyName;
    }
}
