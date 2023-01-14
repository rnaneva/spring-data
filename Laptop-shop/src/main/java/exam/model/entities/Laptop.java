package exam.model.entities;

import exam.model.entities.enums.WarrantyType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity{

    @Column(name = "mac_address", nullable = false, unique = true)
    private String macAddress;

    @Column(name = "cpu_speed", nullable = false)
    private double cpuSpeed;

    @Basic
    private int ram;

    @Basic
    private int storage;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarrantyType warrantyType;

    @ManyToOne
    private Shop shop;

    public Laptop(){}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String info(){
        return "Laptop " + this.macAddress + " - " +
                String.format(".%2f", this.cpuSpeed) + " - " + this.ram + " - " + this.storage;
    }
}
