package softuni.exam.models.dto.offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "apartment")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentIdDTO {

    @XmlElement (name = "id")
    private long id;

    public ApartmentIdDTO(){}

    public long getId() {
        return id;
    }
}
