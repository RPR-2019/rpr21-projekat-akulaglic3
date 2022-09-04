package ba.unsa.etf.rpr.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class StartController {
    public Label lbRegMsg;
    public Label lbAccTypeMsg;
    public Label lbHeadline;
    public Button btnUserAccount;
    public Button btnApotAcc;
    public Button btnCreateAcc;
    public Button btnExit;
    public Menu menuFile;
    public MenuItem menuCreateAcc;
    public MenuItem menuExit;
    public Menu menuVew;
    public Menu menuLanguage;
    public Menu menuTheme;
    public Menu menuHelp;
    public MenuItem menuItemHelp;
    public MenuItem menuAbout;
    public MenuItem menuKeyboard;
    public MenuItem menuDark;
    public MenuItem menuLight;
    @FXML
    private ImageView idImage;

    private ResourceBundle bundle;
    private boolean isDarkModeOn = false;

    @FXML
    public void initialize(){
        idImage.setImage(new Image("\\images\\Logo.png"));
    }


    private void changeCurrentLabels(){
        lbHeadline.setText(bundle.getString("headline"));
        lbAccTypeMsg.setText(bundle.getString("accountTypeMsg"));
        lbRegMsg.setText(bundle.getString("regMsg"));

        btnUserAccount.setText(bundle.getString("userAcc"));
        btnApotAcc.setText(bundle.getString("apotAcc"));
        btnCreateAcc.setText(bundle.getString("createAcc"));
        btnExit.setText(bundle.getString("exit"));

        menuFile.setText(bundle.getString("file"));
        menuAbout.setText(bundle.getString("about"));
        menuVew.setText(bundle.getString("view"));
        menuExit.setText(bundle.getString("exit"));
        menuHelp.setText(bundle.getString("help"));
        menuItemHelp.setText(bundle.getString("help"));
        menuKeyboard.setText(bundle.getString("keyboardShortcuts"));
        menuLanguage.setText(bundle.getString("language"));
        menuTheme.setText(bundle.getString("theme"));
        menuLight.setText(bundle.getString("themeLight"));
        menuDark.setText(bundle.getString("themeDark"));
    }

    public void actionOpenLoginUser(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/login_user.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        LoginUserController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        myStage.show();

    }

    public void actionOpenLoginApothecary(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/login_apothecary.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        LoginApothecaryController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionRegister(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/option.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        OptionController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionExit(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void setDarkMode(boolean darkMode) {
        Scene scene = btnExit.getScene();
        isDarkModeOn = darkMode;
        if (!darkMode) {
            scene.getStylesheets().remove("/css/dark_theme.css");
        }else {
            scene.getStylesheets().add("/css/dark_theme.css");
        }
    }

    public void actionLightTheme(ActionEvent actionEvent) {
        isDarkModeOn = false;
        Scene scene = lbRegMsg.getScene();
        scene.getStylesheets().remove("/css/dark_theme.css");
    }
    public void actionDarkTheme(ActionEvent actionEvent) {
        isDarkModeOn = true;
        Scene scene = lbRegMsg.getScene();
        scene.getStylesheets().add("/css/dark_theme.css");
    }

    public void actionHelp(ActionEvent actionEvent) {
    }

    public void actionAbout(ActionEvent actionEvent) {
    }

    public void actionBosanski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        bundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
        changeCurrentLabels();
    }

    public void actionEnglish(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "US"));
        bundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
        changeCurrentLabels();
    }

    public void actionKeyboardShortcuts(ActionEvent actionEvent) {
    }
}
