package cz.cvut.fel.ear.covid.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.cvut.fel.ear.covid.service.tools.ToolImplementation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tool extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = true)
    @Column(nullable = true)
    private String shortDescription;

    @ManyToMany
    @OrderBy("id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<User> subscribers;

    @Transient
    private ToolImplementation toolImplementation;

    public ToolImplementation getToolImplementation() {
        return toolImplementation;
    }

    public void setToolImplementation(ToolImplementation toolImplementation) {
        this.toolImplementation = toolImplementation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    public boolean addUserToSubscribers(User user){
        if(subscribers==null) return false;
        if(this.subscribers == null){
            this.subscribers = new ArrayList<>();
        }
        subscribers.add(user);
        return true;
    }

    public boolean removeUserFromSubscribers(User user){
        if(user==null) return false;
        if(this.subscribers.contains(user)){
            subscribers.remove(user);
        }
        return true;
    }
}
