package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Models.Item;
import ba.unsa.etf.rpr.Models.User;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainUserController {
    public Label lbWelcome;
    public TextField tfTotalPrice;
    public ListView listItems;
    public Button btnRemoveDrug;
    public Button btnCheckout;

    private User currentUser;
    private UserDAO userDAO;
    private ObservableList<Item> checkoutItems;
    @FXML
    void initialize() throws SQLException {
        listItems.setItems(checkoutItems);

        Double totalAmount = Double.valueOf(0);
        for (Item item: checkoutItems) {
            totalAmount += item.getAmount()*item.getDrug().getPrice();
        }

        tfTotalPrice.setEditable(false);
        tfTotalPrice.setText(totalAmount.toString());

        listItems.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem == null){
                btnRemoveDrug.setDisable(true);
            }else {
                btnRemoveDrug.setDisable(false);
            }
        });

        Bindings.isEmpty(listItems.getItems()).addListener(
                (obs, wasEmpty, isNowEmpty) -> btnCheckout.setDisable(isNowEmpty));

        if (Locale.getDefault().equals(new Locale("bs", "BA"))){
            lbWelcome.setText("Dobrodošao " + currentUser.getUsername());
        }else {
            lbWelcome.setText("Welcome " + currentUser.getUsername());
        }
    }

    public MainUserController(User user) throws SQLException {
        userDAO = UserDAO.getInstance();
        currentUser = user;
        checkoutItems = userDAO.getCheckoutItemsForUser(currentUser);
    }

    public void actionLogout(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/start.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        Stage stage = (Stage) tfTotalPrice.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void actionBuyMenu(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buy_menu.fxml"), bundle);

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));

        BuyMenuController controller = loader.getController();
        controller.initUser(currentUser);
        stage.showAndWait();
    }

    public void actionEditAccount(ActionEvent actionEvent) {
    }

    public void actionRemoveDrug(ActionEvent actionEvent) {
        userDAO.removeItem((Item) listItems.getSelectionModel().getSelectedItem());

        checkoutItems = userDAO.getCheckoutItemsForUser(currentUser);
        listItems.setItems(checkoutItems);
        listItems.refresh();


        Double totalAmount = Double.valueOf(0);
        for (Item item: checkoutItems) {
            totalAmount += item.getAmount()*item.getDrug().getPrice();
        }
        tfTotalPrice.setText(totalAmount.toString());
    }

    public void actionCheckout(ActionEvent actionEvent) {
    }
}
