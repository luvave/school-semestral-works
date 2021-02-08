package cz.cvut.fel.ear.covid.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@DiscriminatorValue("TREATMENT")
public class Treatment extends BasicPost{

    @Basic(optional = false)
    @Column(nullable = false)
    private String shortDescription;

    @OneToOne(mappedBy = "associatedPost")
    @JoinColumn(name = "article_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Article medicine;

    @Basic(optional = false)
    @Column(nullable = false)
    private int treatmentDuration;

    @Basic(optional = false)
    @Column(nullable = false)
    private int effectivness;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Article getMedicine() {
        return medicine;
    }

    public void setMedicine(Article medicine) {
        this.medicine = medicine;
    }

    public int getTreatmentDuration() {
        return treatmentDuration;
    }

    public void setTreatmentDuration(int treatmentDuration) {
        this.treatmentDuration = treatmentDuration;
    }

    public int getEffectivness() {
        return effectivness;
    }

    public void setEffectivness(int effectivness) {
        this.effectivness = effectivness;
    }
}
