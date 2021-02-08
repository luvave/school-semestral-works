package cz.cvut.fel.ear.covid.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("TESTING_CENTER")
public class TestingCenter extends BasicPost{

    @Basic(optional = false)
    @Column(nullable = false)
    private String city;

    @Basic(optional = false)
    @Column(nullable = false)
    private String street;

    @Basic(optional = false)
    @Column(nullable = false)
    private int houseNumber;

    @Basic(optional = true)
    @Column(nullable = true)
    private String openingHours;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
}
