package softuni.exam.models.dto.apartment;

import softuni.exam.models.entity.enums.ApartmentType;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "apartment")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentsSeedDTO {

    @XmlElement
    @NotNull
    private ApartmentType apartmentType;

    @XmlElement
    @NotNull
    @DecimalMin("40.00")
    private double area;

    @XmlElement(name = "town")
    private String townName;

    public ApartmentsSeedDTO(){
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public double getArea() {
        return area;
    }

    public String getTownName() {
        return townName;
    }
}
