package model;

public class Inhouse extends Part{
    private int machineId;
    public Inhouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * //TODO - Update note
     * @return
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * //TODO - Update note
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
