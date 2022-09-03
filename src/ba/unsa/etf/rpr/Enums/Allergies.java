package ba.unsa.etf.rpr.Enums;

import ba.unsa.etf.rpr.Exceptions.IllegalAllergyType;

import java.util.Arrays;
import java.util.Optional;

public enum Allergies {
    aspirinAllergy(0),
    antibioticsAllergy(1),
    nonsteroidalAntiInflammatoryAllergy(2),
    sulfaDrugsAllergy(3),
    chemotherapyDrugsAllergy(4),
    insulinAllergy(5),
    antiseizureDrugsAllergy(6);

    public final int value;

    Allergies(int value) {
        this.value = value;
    }

    public static Allergies valueOf(int value) throws IllegalAllergyType {
        Optional<Allergies> optionalAllergies = Arrays.stream(values())
                .filter(allergy -> allergy.value == value)
                .findFirst();

        if (optionalAllergies.isPresent()){
            return optionalAllergies.get();
        }
        throw new IllegalAllergyType("Allergy associated with the received integer does not exit!");
    }
}
