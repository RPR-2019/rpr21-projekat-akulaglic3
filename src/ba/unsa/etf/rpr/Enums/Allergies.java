package ba.unsa.etf.rpr.Enums;

import ba.unsa.etf.rpr.Exceptions.IllegalAllergyType;

import java.util.Arrays;
import java.util.Optional;

public enum Allergies {
    ASPIRIN_ALLERGY(0),
    ANTIBIOTICS_ALLERGY(1),
    NONSTEROIDAL_ANTI_INFLAMMATORY_ALLERGY(2),
    SULFA_DRUGS_ALLERGY(3),
    CHEMOTHERAPY_DRUGS_ALLERGY(4),
    INSULIN_ALLERGY(5),
    ANTISEIZURE_DRUGS_ALLERGY(6);

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
