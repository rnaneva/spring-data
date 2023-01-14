package softuni.exam.models.dto.offer;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;


@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDTO {

    @Positive
    @XmlElement
    private BigDecimal price;

    @XmlElement(name = "agent")
    private AgentNameDTO agentName;

    @XmlElement(name = "apartment")
    private ApartmentIdDTO apartmentId;

    @XmlElement
    @NotNull
    private String publishedOn;

    public OfferSeedDTO(){}

    public BigDecimal getPrice() {
        return price;
    }

    public AgentNameDTO getAgentName() {
        return agentName;
    }

    public ApartmentIdDTO getApartmentId() {
        return apartmentId;
    }

    public String getPublishedOn() {
        return publishedOn;
    }
}
