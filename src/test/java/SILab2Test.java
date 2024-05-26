import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SILab2Test {

    private List<Item> createList(Item... elems){
        return new ArrayList<>(Arrays.asList(elems));
    }

    @Test
    void MultipleConditionsTest() {
        // F && X && X = F
        List<Item> items1 = createList(new Item("bread","563218", 250, 0.0f));
        assertFalse(SILab2.checkCart(items1, 200));

        // T && F && X = F
        List<Item> items2 = createList(new Item("milk","284695", 320, 0.0f));
        assertFalse(SILab2.checkCart(items2, 300));

        // T && T && F = F
        List<Item> items3 = createList(new Item("water","784512", 410, 0.1f));
        assertFalse(SILab2.checkCart(items3, 40));

        // T && T && T = T
        List<Item> items4 = createList(new Item("cheese","0546231", 450, 0.17f));
        assertTrue(SILab2.checkCart(items4, 600));
    }
    @Test
    void EveryBranchTest(){
        RuntimeException ex;

        //null
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, 6000));
        assertTrue(ex.getMessage().contains("allItems list can't be null!"));

        //Invalid character
        Item bread = new Item("bread","12456", 100, 0.1f);
        Item milk = new Item("milk", "012423", 400, 0.12f);
        Item noname = new Item("", "42a5c6", 300, 0);
        List<Item> items = createList(bread, milk, noname);
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, 6000));
        assertTrue(ex.getMessage().contains("Invalid character in item barcode!"));

        //No barcode
        Item water = new Item("water",null, 100, 10);
        List<Item> items2 = createList(bread, milk, water);
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items2, 6000));
        assertTrue(ex.getMessage().contains("No barcode!"));

        //All valid, sum > payment
        Item cheese = new Item("cheese","457816", 20, 10);
        List<Item> items3 = createList(bread, milk, cheese);
        assertFalse(SILab2.checkCart(items3,200));

        //All valid, sum < payment
        List<Item> items4 = createList(bread, milk, cheese);
        assertTrue(SILab2.checkCart(items4,3000));
    }
}
