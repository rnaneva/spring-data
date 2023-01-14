package softuni.exam.models.dto.offer;

import java.math.BigDecimal;

public class BestOfferDTO {

    private String agentFirstName;
    private String agentLastName;
    private double apartmentArea;
    private long id;
    private String apartmentTownName;
    private BigDecimal price;

    public BestOfferDTO() {
    }

    public String getAgentFirstName() {
        return agentFirstName;
    }

    public void setAgentFirstName(String agentFirstName) {
        this.agentFirstName = agentFirstName;
    }

    public String getAgentLastName() {
        return agentLastName;
    }

    public void setAgentLastName(String agentLastName) {
        this.agentLastName = agentLastName;
    }

    public double getApartmentArea() {
        return apartmentArea;
    }

    public void setApartmentArea(double apartmentArea) {
        this.apartmentArea = apartmentArea;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApartmentTownName() {
        return apartmentTownName;
    }

    public void setApartmentTownName(String apartmentTownName) {
        this.apartmentTownName = apartmentTownName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Agent %s %s with offer №:" + this.id + "\n" +
                "   \t\t-Apartment area: %.2f\n" +
                "   \t\t--Town: %s\n" +
                "   \t\t---Price: %.2f$",
                this.agentFirstName, this.agentLastName,
                this.apartmentArea,
                this.apartmentTownName,
                this.price);
    }
}

