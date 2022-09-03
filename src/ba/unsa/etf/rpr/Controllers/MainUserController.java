package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Models.Item;
import ba.unsa.etf.rpr.Models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class MainUserController {
    public Label lbWelcome;
    public TextField tfTotalPrice;
    public ListView listItems;


    private User currentUser;
    private UserDAO userDAO;
    private ObservableList<Item> checkoutItems;
    @FXML
    void initialize() throws SQLException {
        listItems.setItems(checkoutItems);
    }

    public MainUserController(User user) throws SQLException {
        userDAO = UserDAO.getInstance();
        currentUser = user;
        checkoutItems = userDAO.getCheckoutItemsForUser(currentUser);
    }

    public void actionLogout(ActionEvent actionEvent) {
    }

    public void actionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void actionBuyMenu(ActionEvent actionEvent) {
    }

    public void actionEditAccount(ActionEvent actionEvent) {
    }

    public void actionRemoveDrug(ActionEvent actionEvent) {
    }

    public void actionCheckout(ActionEvent actionEvent) {
    }
}
