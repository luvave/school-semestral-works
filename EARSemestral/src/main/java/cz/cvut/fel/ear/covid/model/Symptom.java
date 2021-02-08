package cz.cvut.fel.ear.covid.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("SYMPTOM")
public class Symptom extends BasicPost{

    @Basic(optional = false)
    @Column(nullable = false)
    private String shortDescription;

    @Basic(optional = false)
    @Column(nullable = false)
    private int percentage;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
