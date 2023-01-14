package exam.model.dtos.shop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlAccessorType (XmlAccessType.FIELD)
@XmlRootElement(name = "shops")
public class WrapperShopSeedDTO {

    @XmlElement(name = "shop")
    private List<ShopSeedDTO> shops;

    public WrapperShopSeedDTO(){
        this.shops = new LinkedList<>();
    }

    public List<ShopSeedDTO> getShops() {
        return shops;
    }
}
