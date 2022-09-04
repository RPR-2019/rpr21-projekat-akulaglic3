package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
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
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainUserController {

    public TextField tfTotalPrice;
    public ListView listItems;

    public Menu menuFile;
    public Menu menuLanguage;
    public MenuItem menuLogout;
    public MenuItem menuExit;
    public Menu menuHelp;
    public MenuItem menuKeyboard;
    public MenuItem menuItemHelp;
    public MenuItem menuAbout;
    public Menu menuTheme;
    public MenuItem menuDark;
    public MenuItem menuLight;

    public Button btnRemoveDrug;
    public Button btnCheckout;
    public Button btnLogout;
    public Button btnExit;
    public Button btnBuyMenu;
    public Button btnEditAcc;

    public Label lbWelcome;
    public Label lbTotalPrice;
    public Label lbYourCheckout;

    private ApothecaryDAO apothecaryDAO = ApothecaryDAO.getInstance();
    private User currentUser;
    private UserDAO userDAO;
    private ObservableList<Item> checkoutItems;
    private ResourceBundle bundle;
    private boolean isDarkModeOn = false;

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
                (obs, wasEmpty, isNowEmpty) -> {
                    btnCheckout.setDisable(isNowEmpty);
                });

        if (Locale.getDefault().equals(new Locale("bs", "BA"))){
            lbWelcome.setText("Dobrodošao " + currentUser.getUsername());
        }else {
            lbWelcome.setText("Welcome " + currentUser.getUsername());
        }

        if (listItems.getItems().isEmpty()){
            btnCheckout.setDisable(true);
            btnRemoveDrug.setDisable(true);
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

        StartController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);

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

        controller.setDarkMode(isDarkModeOn);
        stage.setOnHiding(windowEvent -> {
            ObservableList<Item> itemList = userDAO.getCheckoutItemsForUser(currentUser);
            listItems.setItems(itemList);
            listItems.refresh();


            Double totalAmount = Double.valueOf(0);
            for (Item item: itemList) {
                totalAmount += item.getAmount()*item.getDrug().getPrice();
            }
            tfTotalPrice.setText(totalAmount.toString());
        });
        stage.showAndWait();

        if (!listItems.getItems().isEmpty()){
            btnCheckout.setDisable(false);
        }
    }

    public void actionEditAccount(ActionEvent actionEvent) throws IOException {
        EditAccountUserController controller = new EditAccountUserController(currentUser);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/edit_account_user.fxml" ), bundle);

        loader.setController(controller);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        controller.setDarkMode(isDarkModeOn);

        stage.setTitle("eHealth");
        stage.showAndWait();
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

    public void actionCheckout(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/checkout.fxml" ), bundle);

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        CheckoutController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);

        stage.setOnHiding(windowEvent -> {
            if (controller.getConfirmed()){
                List<Item> itemList = listItems.getItems();
                for (Item item: itemList ) {
                    apothecaryDAO.addProfit(item.getDrug().getApothecary(), item.getAmount()*item.getDrug().getPrice());
                    userDAO.removeItem(item);
                }

                checkoutItems = userDAO.getCheckoutItemsForUser(currentUser);
                listItems.setItems(checkoutItems);
                listItems.refresh();
                tfTotalPrice.setText("0.0");

                btnCheckout.setDisable(true);
            }
        });
        stage.showAndWait();
    }


    private void changeCurrentLabels(){
        lbTotalPrice.setText(bundle.getString("totalPrice"));
        lbYourCheckout.setText(bundle.getString("yourCheckout"));
        if (Locale.getDefault().equals(new Locale("bs", "BA"))){
            lbWelcome.setText("Dobrodošao " + currentUser.getUsername());
        }else {
            lbWelcome.setText("Welcome " + currentUser.getUsername());
        }

        btnRemoveDrug.setText(bundle.getString("removeDrug"));
        btnCheckout.setText(bundle.getString("checkout"));
        btnEditAcc.setText(bundle.getString("editAcc"));
        btnLogout.setText(bundle.getString("logout"));
        btnExit.setText(bundle.getString("exit"));
        btnBuyMenu.setText(bundle.getString("buyMenu"));


        //menuFile.setText(bundle.getString("file"));
        menuAbout.setText(bundle.getString("about"));
        menuExit.setText(bundle.getString("exit"));
        //menuHelp.setText(bundle.getString("help"));
        menuLogout.setText(bundle.getString("logout"));
        menuItemHelp.setText(bundle.getString("help"));
        menuKeyboard.setText(bundle.getString("keyboardShortcuts"));
        menuLanguage.setText(bundle.getString("language"));
        menuTheme.setText(bundle.getString("theme"));
        menuLight.setText(bundle.getString("themeLight"));
        menuDark.setText(bundle.getString("themeDark"));
    }

    public void actionBosanski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        bundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
        changeCurrentLabels();
    }

    public void actionEngleski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "US"));
        bundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
        changeCurrentLabels();
    }

    public void actionKeyboardShortcuts(ActionEvent actionEvent) {
    }

    public void actionHelp(ActionEvent actionEvent) {
    }

    public void actionAbout(ActionEvent actionEvent) {
    }

    public void actionLightTheme(ActionEvent actionEvent) {
        isDarkModeOn = false;
        Scene scene = tfTotalPrice.getScene();
        scene.getStylesheets().remove("/css/dark_theme.css");
    }
    public void actionDarkTheme(ActionEvent actionEvent) {
        isDarkModeOn = true;
        Scene scene = tfTotalPrice.getScene();
        scene.getStylesheets().add("/css/dark_theme.css");
    }

    public void setDarkMode(boolean darkMode) {
        Scene scene = tfTotalPrice.getScene();
        isDarkModeOn = darkMode;
        if (!darkMode) {
            scene.getStylesheets().remove("/css/dark_theme.css");
        }else {
            scene.getStylesheets().add("/css/dark_theme.css");
        }
    }
}
