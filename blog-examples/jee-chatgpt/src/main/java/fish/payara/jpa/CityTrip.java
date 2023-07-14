package fish.payara.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class CityTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "landmark_generator")
    @SequenceGenerator(name = "landmark_generator", sequenceName = "landmark_sequence", allocationSize = 5)
    int id;

    @NotEmpty
    String cityName;

    @NotNull
    Integer budget;

    @NotEmpty
    @Column(columnDefinition = "text")
    String pointsOfInterest;

    @NotEmpty
    String region;

    public CityTrip() {
    }

    public CityTrip(@NotEmpty String cityName, @NotNull Integer budget, @NotEmpty String pointsOfInterest,
            @NotEmpty String region) {
        this.cityName = cityName;
        this.budget = budget;
        this.pointsOfInterest = pointsOfInterest;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(String poiJson) {
        this.pointsOfInterest = poiJson;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "CityTrip [id=" + id + ", cityName=" + cityName + ", budget=" + budget + ", pointsOfInterest="
                + pointsOfInterest + ", region=" + region + "]";
    }
}
