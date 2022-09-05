package ba.unsa.etf.rpr.MockingTests;


import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Enums.AdministrationTypes;
import ba.unsa.etf.rpr.Enums.Allergies;
import ba.unsa.etf.rpr.Exceptions.IllegalAdministrationType;
import ba.unsa.etf.rpr.Exceptions.IllegalAllergyType;
import ba.unsa.etf.rpr.Models.Drug;
import ba.unsa.etf.rpr.Models.Item;
import ba.unsa.etf.rpr.Models.User;
import ba.unsa.etf.rpr.Utility.Validator;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DAOTest {

    @Test
    void userDAOTest1() {
        UserDAO userDAO = null;
        Validator validator = null;
        try {
            userDAO = UserDAO.getInstance();
            validator = Validator.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertNotNull(userDAO);
        assertNotNull(validator);
    }

    @Test
    void apothecaryDAOTest1() {
        ApothecaryDAO apothecaryDAO = null;
        try {
            apothecaryDAO = ApothecaryDAO.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(apothecaryDAO);
        List<Drug> drugList = apothecaryDAO.getAllDrugs();

        if (!drugList.isEmpty()){
            assertNotNull(drugList.get(0).getPrice());
        }
    }
}
