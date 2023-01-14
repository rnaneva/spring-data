package softuni.exam.models.dto.apartment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class WrapperApartmentSeedDTO {

    @XmlElement(name = "apartment")
    private List<ApartmentsSeedDTO> apartments;

    public WrapperApartmentSeedDTO(){
        this.apartments = new LinkedList<>();
    }

    public List<ApartmentsSeedDTO> getApartments() {
        return apartments;
    }
}
