package softuni.exam.models.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownSeedDTO {

    @Size(min = 2)
    @NotNull
    private String townName;


    @Positive
    private int population;

    public TownSeedDTO() {}

    public String getTownName() {
        return townName;
    }

    public int getPopulation() {
        return population;
    }
}
