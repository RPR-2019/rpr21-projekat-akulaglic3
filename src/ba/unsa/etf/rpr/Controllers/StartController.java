package ba.unsa.etf.rpr.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
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
    public Menu menuView;
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
    private final String TRANSLATION = "Translation";

    @FXML
    public void initialize(){

        idImage.setImage(new Image("\\images\\Logo.png"));
        Image userImage = new Image("/icons/user.png");
        ImageView viewUser = new ImageView(userImage);
        viewUser.setFitHeight(10);
        viewUser.setPreserveRatio(true);
        menuCreateAcc.setGraphic(viewUser);

        Image helpImage = new Image("/icons/question.png");
        ImageView viewHelp = new ImageView(helpImage);
        viewHelp.setFitHeight(10);
        viewHelp.setPreserveRatio(true);
        menuItemHelp.setGraphic(viewHelp);
    }


    private void changeCurrentLabels(){
        lbHeadline.setText(bundle.getString("headline"));
        lbAccTypeMsg.setText(bundle.getString("accountTypeMsg"));
        lbRegMsg.setText(bundle.getString("regMsg"));

        btnUserAccount.setText(bundle.getString("userAcc"));
        btnApotAcc.setText(bundle.getString("apotAcc"));
        btnCreateAcc.setText(bundle.getString("createAcc"));
        btnExit.setText(bundle.getString("exit"));

        menuAbout.setText(bundle.getString("about"));
        menuExit.setText(bundle.getString("exit"));
        menuItemHelp.setText(bundle.getString("help"));
        menuKeyboard.setText(bundle.getString("keyboardShortcuts"));
        menuLanguage.setText(bundle.getString("language"));
        menuTheme.setText(bundle.getString("theme"));
        menuLight.setText(bundle.getString("themeLight"));
        menuDark.setText(bundle.getString("themeDark"));
    }

    public void actionOpenLoginUser(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
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
        ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
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
        ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/option.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        OptionController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);

        Stage stage = (Stage) lbAccTypeMsg.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionExit(ActionEvent actionEvent) {
        Stage stage = (Stage) lbRegMsg.getScene().getWindow();
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
        File file = null;
        if (Locale.getDefault().equals(new Locale("bs", "BA"))){
            file = new File("resources/pdf/Help Guide - Bosanski.pdf");
        }else{
            file = new File("resources/pdf/Help Guide - English.pdf");
        }

        try {
            openFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionAbout(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/about.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        AboutController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);
        myStage.showAndWait();
    }

    public void actionBosanski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        bundle = ResourceBundle.getBundle(TRANSLATION, Locale.getDefault());
        changeCurrentLabels();
    }

    public void actionEnglish(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "US"));
        bundle = ResourceBundle.getBundle(TRANSLATION, Locale.getDefault());
        changeCurrentLabels();
    }

    public void actionKeyboardShortcuts(ActionEvent actionEvent) {
        File file = new File("resources/pdf/Keyboard Shortcuts PDF.pdf");
        try {
            openFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openFile(File file) throws Exception {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
