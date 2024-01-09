package proj.test;

/**
 * InHouse Parts Class that extends Part
 * @author TDavenport
 */
public class InHouse extends Part {

    private int machineID;

    /**
     * Constructor for InHouse Part
     * @param id ID number of part
     * @param name Name of part
     * @param price Price of part
     * @param stock Inventory stock of part
     * @param min Minimum Inventory level of stocked part
     * @param max Maximum Inventory level of stocked part
     * @param machineID Machine ID of InHouse machine part was produced with
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Setter Function for Machine ID
     * @param machineID Machine ID of part
     */
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }

    /**
     * Getter Function for Machine ID
     * @return Machine ID of part
     */
    public int getMachineID(){
        return machineID;
    }

}
