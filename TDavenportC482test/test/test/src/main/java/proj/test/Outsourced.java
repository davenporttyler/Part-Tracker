package proj.test;

/**
 * Outsourced Parts Class that extends Part
 * @author TDavenport
 */

public class Outsourced extends Part {

    private String companyName;

    /**
     *
     * @param id ID number of part
     * @param name Name of part
     * @param price Price of part
     * @param stock Inventory stock of part
     * @param min Minimum Inventory level of stocked part
     * @param max Maximum Inventory level of stocked part
     * @param companyName Name of Outsourced Company Part was produced from
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     * Setter Function for Company Name
     * @param companyName Company Name of part
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Getter Function for Company Name
     * @return Company Name of part
     */
    public String getCompanyName(){
        return companyName;
    }


}
