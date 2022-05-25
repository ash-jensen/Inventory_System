package model;

/**
 * This class makes InHouse objects that extend Part and include getters/setters for id, name, price, stock, min, max, and machineID.
 * @author Ashley Jensen
 */
public class InHouse extends Part {
    /**
     * Variable used to hold the machineId of an In-House part.
     */
    private int machineId;

    /**
     * InHouse object constructor with id, name, price, stock, min, max, and machineID
     * @param id assigns id
     * @param name assigns name
     * @param price assigns price
     * @param stock assigns stock
     * @param min assigns min
     * @param max assigns max
     * @param machineId assigns machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        // Part superclass constructor
        super(id, name, price, stock, min, max);

        // MachineID variable assigned
        this.machineId = machineId;
    }

    /**
     * This method sets the machineId variable to machineId parameter.
     * @param machineId sets machineId
     */
    public void setMachineId(int machineId) {
        // sets machineID to passed in value
        this.machineId = machineId;
    }

    /**
     * This method returns machineId.
     * @return returns machine id.
     */
    public int getMachineId() {
        return machineId;
    }


}
