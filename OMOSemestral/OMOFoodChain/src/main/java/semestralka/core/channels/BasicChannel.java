/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.channels;

import java.util.Collection;
import java.util.List;
import semestralka.core.entities.AbstractParty;

/**
 *
 * @author travnja5
 */
public abstract class BasicChannel {
    protected List<AbstractParty> parties;
    
    
        /**
     * Adding a party to this channel
     * @param party to be added
     */
    public void addParty(AbstractParty party){
        parties.add(party);
    }
    /**
     * Adding multiple parties to the channel
     * Mostly used at initialization
     * @param parties Collection of parties to be added
     */
    public void addParty(Collection parties){
        parties.addAll(parties);
    }
    
    /**
     * Remove party from this channel
     * @param party Party to be removed
     */
    public void removeParty(AbstractParty party){
        parties.remove(party);
    }

}
