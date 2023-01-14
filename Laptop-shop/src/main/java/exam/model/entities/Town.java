package exam.model.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    @Basic
    private int population;

    @Column(name = "travel_guide", nullable = false, columnDefinition = "text")
    private String travelGuide;

    public Town(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }


    public String info(){
        return "Town " + this.name;
    }
}
