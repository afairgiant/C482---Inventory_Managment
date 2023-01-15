package model;

/**
 * Outsourced
 * Class to describe the Outsourced Parts
 */

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * Get the companyName for Outsourced Part
     * @return gets the companyName for Outsourced Part
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * Set the companyName for outsourced part
     * @param companyName sets the company name for Outsourced Part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
