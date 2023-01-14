package exam.model.dtos.sown;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class WrapperTownSeedDTO {

    @XmlElement(name = "town")
    private List<TownSeedDTO> towns;

    public WrapperTownSeedDTO(){
        this.towns = new LinkedList<>();
    }

    public List<TownSeedDTO> getTowns() {
        return towns;
    }
}
