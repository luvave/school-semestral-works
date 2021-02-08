package semestralka.core.entities;

import org.junit.Test;
import semestralka.control.PartyController;
import semestralka.core.product.ProductType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import semestralka.control.WorldController;

public class PartyTest {

    public PartyTest() {
    }

    @Test
    public void testSameParty() {
        //Can we buy similar different parties in same channels?
        PartyController party = new PartyController();
        WorldController world = new WorldController(party);
        Market market = world.createBasicMarket();
        world.createAllChannels(market);
        List<ProductType> ableToDo = new ArrayList<>();
        ableToDo.add(ProductType.GRAIN);
        ableToDo.add(ProductType.SUNFLOWER);
        ableToDo.add(ProductType.POTATOES);
        ableToDo.add(ProductType.PIG);
        ProductFactory farmerHouse = new ProductFactory();
        Farmer farmer = new Farmer("OtherFarmer", 100,market,farmerHouse,ableToDo);
        Farmer farmer2 = party.createFullFarmer(market, "Farmer", 10);
        assertEquals(farmer.ableToDo, farmer2.ableToDo);
        assertEquals(farmer.tradingWith, farmer2.tradingWith);
    }

    @Test
    public void testProccessorFarmer(){
        //Did processor join same channel?
        PartyController party = new PartyController();
        WorldController world = new WorldController(party);
        Market market = world.createBasicMarket();
        world.createAllChannels(market);
        Farmer farmer = party.createFullFarmer(market, "Farmer", 10);
        Processor processor = party.createFullProccessor(market, "Processor", 10);
        processor.joinChannel(ProductType.GRAIN);
        assertEquals(true, processor.tradingWith.contains(farmer.tradingWith.get(0)));
    }

}
