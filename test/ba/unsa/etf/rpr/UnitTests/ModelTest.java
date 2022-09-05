package ba.unsa.etf.rpr.UnitTests;

import ba.unsa.etf.rpr.Enums.AdministrationTypes;
import ba.unsa.etf.rpr.Enums.Allergies;
import ba.unsa.etf.rpr.Models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    public void testAdminEquals() {
        Admin firstAdmin = new Admin(1,"Apothecary","Apothecary");
        Admin secondAdmin = new Admin(2, "Apothecary", "Apothecary");
        Admin thirdAdmin = new Admin(3, "test", "test");

        assertEquals(firstAdmin, secondAdmin);
        assertNotEquals(firstAdmin, thirdAdmin);
    }

    @Test
    public void testApothecaryEquals() {
        Apothecary firstApothecary = new Apothecary(1,"Apothecary1","Apothecary2","Apothecary3", "Apothecary4",
                100D);
        Apothecary secondApothecary = new Apothecary(2,"Apothecary1","Apothecary2","Apothecary3", "Apothecary4",
                100D);
        Apothecary thirdApothecary = new Apothecary(1,"Apothecary8","Apothecary2","Apothecary3", "Apothecary4",
                150D);

        assertTrue(firstApothecary.equals(secondApothecary));
        assertFalse(firstApothecary.equals(thirdApothecary));
    }

    @Test
    public void testDrugEquals() {
        Apothecary firstApothecary = new Apothecary(1,"Apothecary1","Apothecary2","Apothecary3", "Apothecary4",
                100D);
        Apothecary secondApothecary = new Apothecary(2,"Apothecary1","Apothecary2","Apothecary3", "Apothecary4",
                100D);
        Apothecary thirdApothecary = new Apothecary(1,"Apothecary8","Apothecary2","Apothecary3", "Apothecary4",
                150D);


        Drug firstDrug = new Drug(1,"name","name","name","test","test", LocalDate.EPOCH, AdministrationTypes.ORAL_ADMINISTRATION
        , new byte[] {1},15D, firstApothecary);
        Drug secondDrug = new Drug(2,"name","name","name","test","test", LocalDate.EPOCH, AdministrationTypes.ORAL_ADMINISTRATION
                , new byte[] {1},15D, secondApothecary);
        Drug thirdDrug = new Drug(1,"name","name","name","test","test", LocalDate.EPOCH, AdministrationTypes.ORAL_ADMINISTRATION
                , new byte[] {1},15D, thirdApothecary);

        assertEquals(firstDrug, secondDrug);
        assertNotEquals(firstDrug, thirdDrug);
    }

    @Test
    public void testDrugToString() {
        Apothecary firstApothecary = new Apothecary(1,"Apothecary1","Apothecary2","Apothecary3", "Apothecary4",
                100D);
        Drug firstDrug = new Drug(1,"name","name","name","test","test", LocalDate.EPOCH, AdministrationTypes.ORAL_ADMINISTRATION
                , null,15D, firstApothecary);

        assertEquals("name(name), 15.0€", firstDrug.toString());
    }

    @Test
    public void testItemEquals() {
        Apothecary firstApothecary = new Apothecary(1,"Apothecary1","Apothecary2","Apothecary3", "Apothecary4",
                100D);
        Apothecary secondApothecary = new Apothecary(2,"Apothecary1","Apothecary2","Apothecary3", "Apothecary4",
                100D);
        Apothecary thirdApothecary = new Apothecary(1,"Apothecary8","Apothecary2","Apothecary3", "Apothecary4",
                150D);


        Drug firstDrug = new Drug(1,"name","name","name","test","test", LocalDate.EPOCH, AdministrationTypes.ORAL_ADMINISTRATION
                , null,15D, firstApothecary);
        Drug secondDrug = new Drug(2,"name","name","name","test","test", LocalDate.EPOCH, AdministrationTypes.ORAL_ADMINISTRATION
                , null,15D, secondApothecary);
        Drug thirdDrug = new Drug(1,"name","name","name","test","test", LocalDate.EPOCH, AdministrationTypes.ORAL_ADMINISTRATION
                , null,15D, thirdApothecary);

        Item firstAItem = new Item(1,firstDrug,1,1);
        Item secondItem = new Item(1,secondDrug,1,1);
        Item thirdItem = new Item(1,thirdDrug,2,1);

        assertEquals(firstAItem, secondItem);
        assertNotEquals(firstAItem, thirdItem);
    }

    @Test
    public void testItemToString() {
        Apothecary firstApothecary = new Apothecary(1,"Apothecary1","Apothecary2","Apothecary3", "Apothecary4",
                100D);
        Drug firstDrug = new Drug(1,"name","name","name","test","test", LocalDate.EPOCH, AdministrationTypes.ORAL_ADMINISTRATION
                , null,15D, firstApothecary);
        Item firstAItem = new Item(1,firstDrug,1,1);

        assertEquals("name, name/ Price = 15.0, Amount = 1, Total = 15.0", firstAItem.toString());
    }

    @Test
    public void testUserEquals() {
        User firstUser= new User(1,"user","user","user","user","user",
                "user","user", List.of(Allergies.ASPIRIN_ALLERGY));
        User secondUser = new User(2,"user","user","user","user","user",
                "user","user", List.of(Allergies.ASPIRIN_ALLERGY));
        User thirdUser = new User(3,"user","user","user","user","user",
                "user","user", List.of(Allergies.ANTIBIOTICS_ALLERGY));

        assertEquals(firstUser, secondUser);
        assertNotEquals(firstUser, thirdUser);
    }
}