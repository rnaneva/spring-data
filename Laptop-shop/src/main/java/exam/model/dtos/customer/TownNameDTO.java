package exam.model.dtos.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TownNameDTO {

    private String name;

    public TownNameDTO(){}

    public String getTownName() {
        return name;
    }
}
