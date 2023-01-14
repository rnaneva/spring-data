package exam.model.dtos.laptop;


import java.math.BigDecimal;

public class LaptopExportDTO {

    private String macAddress;
    private double cpuSpeed;
    private int ram;
    private int storage;
    private BigDecimal price;
    private String shopName;
    private String shopTownName;

    public LaptopExportDTO(String macAddress,
                           double cpuSpeed,
                           int ram,
                           int storage,
                           BigDecimal price,
                           String shopName,
                           String shopTownName) {
        this.macAddress = macAddress;
        this.cpuSpeed = cpuSpeed;
        this.ram = ram;
        this.storage = storage;
        this.price = price;
        this.shopName = shopName;
        this.shopTownName = shopTownName;
    }

    public LaptopExportDTO(){}

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTownName() {
        return shopTownName;
    }

    public void setShopTownName(String shopTownName) {
        this.shopTownName = shopTownName;
    }

    public String info(){
        return String.format("Laptop - %s\n" +
                "*Cpu speed - %.2f\n" +
                "**Ram - %d\n" +
                "***Storage - %d\n" +
                "****Price - %.2f\n" +
                "#Shop name - %s\n" +
                "##Town - %s\n",
                this.macAddress,
                this.cpuSpeed,
                this.ram,
                this.storage,
                this.price,
                this.shopName,
                this.shopTownName);
    }
}
