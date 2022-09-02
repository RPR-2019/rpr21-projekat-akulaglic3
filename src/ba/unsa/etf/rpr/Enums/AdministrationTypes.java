package ba.unsa.etf.rpr.Enums;

import ba.unsa.etf.rpr.Exceptions.IllegalAdministrationType;

import java.util.Arrays;
import java.util.Optional;

public enum AdministrationTypes {
    oralAdministration(0), sublingualAdministration(1), rectalAdministration(2),
    parenteralAdministration(3);

    private final int value;

    AdministrationTypes(int value) {
        this.value = value;
    }

    public static AdministrationTypes valueOf(int value) throws IllegalAdministrationType {
        Optional<AdministrationTypes> optionalAdministrationTypes = Arrays.stream(values())
                .filter(legNo -> legNo.value == value)
                .findFirst();

        if (optionalAdministrationTypes.isPresent()){
            return optionalAdministrationTypes.get();
        }
        throw new IllegalAdministrationType("Administration type associated with the received integer does not exit!");
    }
}