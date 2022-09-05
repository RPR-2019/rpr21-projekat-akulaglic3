package ba.unsa.etf.rpr.UnitTests;

import ba.unsa.etf.rpr.Enums.AdministrationTypes;
import ba.unsa.etf.rpr.Enums.Allergies;
import ba.unsa.etf.rpr.Exceptions.IllegalAdministrationType;
import ba.unsa.etf.rpr.Exceptions.IllegalAllergyType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumTest {

    @Test
    void valueOfAllergiesTest() throws IllegalAllergyType {
        assertEquals(Allergies.ANTIBIOTICS_ALLERGY, Allergies.valueOf("ANTIBIOTICS_ALLERGY"));
        assertEquals(Allergies.ANTIBIOTICS_ALLERGY, Allergies.valueOf(1));

        assertThrows(IllegalAllergyType.class, () -> {
            Allergies.valueOf(7);
        }, "Allergy associated with the received integer does not exit!");
    }

    @Test
    void valueOfAdministrationTypesTest() throws IllegalAdministrationType {
        assertEquals(AdministrationTypes.ORAL_ADMINISTRATION, AdministrationTypes.valueOf("ORAL_ADMINISTRATION"));
        assertEquals(AdministrationTypes.ORAL_ADMINISTRATION, AdministrationTypes.valueOf(0));

        assertThrows(IllegalAdministrationType.class, () -> {
            AdministrationTypes.valueOf(7);
        }, "Administration type associated with the received integer does not exit!");
    }
}