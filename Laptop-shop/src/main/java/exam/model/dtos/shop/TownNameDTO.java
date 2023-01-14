package exam.model.dtos.shop;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "town")
public class TownNameDTO {

    @XmlElement(name = "name")
    private String townName;

    public TownNameDTO(){}

    public String getTownName() {
        return townName;
    }
}
