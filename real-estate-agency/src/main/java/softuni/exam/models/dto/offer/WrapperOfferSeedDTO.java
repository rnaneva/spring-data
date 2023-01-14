package softuni.exam.models.dto.offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement ( name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class WrapperOfferSeedDTO {

    @XmlElement(name = "offer")
    private List<OfferSeedDTO> offers;

    public WrapperOfferSeedDTO() {
        this.offers = new LinkedList<>();
    }

    public List<OfferSeedDTO> getOffers() {
        return offers;
    }
}
