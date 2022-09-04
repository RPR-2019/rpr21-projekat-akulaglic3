package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.Models.Apothecary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RegisterApothecaryController {
    public TextField fldName;
    public TextField fldEmail;
    public PasswordField fldPassword;
    public TextField fldAddress;
    public TextField fldPhone;
    private ApothecaryDAO apothecaryDAO;
    private boolean isDarkModeOn = false;

    @FXML
    public void initialize() throws SQLException {
        apothecaryDAO = ApothecaryDAO.getInstance();
    }

    public void actionBack(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/start.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        StartController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionRegister(ActionEvent actionEvent) throws IOException {
        String name = fldName.getCharacters().toString();
        String password = fldPassword.getText(), address = fldAddress.getCharacters().toString();
        String phone = fldPhone.getCharacters().toString();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        if (!isStringCorrectHeavy(name)){
            alertIncorectHeavy(bundle.getString("nameRU"));
        }else if (!isStringCorrectEasy(password)){
            alertIncorrectEasy(bundle.getString("passwordRU"));
        }else if (!isStringCorrectEasy(address)){
            alertIncorrectEasy(bundle.getString("addressRU"));
        }else if (!isStringCorrectEasy(phone)){
            alertIncorrectEasy(bundle.getString("phoneRU"));
        }else if (apothecaryDAO.checkForSameAdminName(name)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("errorRegisteringNameTakenHeadline2"));
            errorAlert.setContentText(bundle.getString("errorRegisteringNameTakenContent2"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else {
            apothecaryDAO.addApothecary(name, fldEmail.getText(),
                    password, address, phone);
            Apothecary newApothecary = apothecaryDAO.getApothecary(name);

            MainApothecaryController controller = new MainApothecaryController(newApothecary);
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/main_apothecary.fxml" ), bundle);

            loader.setController(controller);
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));


            controller.setDarkMode(isDarkModeOn);
            stage.setTitle("eHealth");
            stage.show();

            Node n = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) n.getScene().getWindow();
            currentStage.close();
        }
    }


    private void alertIncorrectEasy(String string) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        errorAlert.setHeaderText(bundle.getString("invalid") + " " + string + "!");
        errorAlert.setContentText(bundle.getString("ent") + " " + string + " " + bundle.getString("errorMsgEasy"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    private void alertIncorectHeavy(String string) {

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        errorAlert.setHeaderText(bundle.getString("invalid") + " " + string + "!");
        errorAlert.setContentText(bundle.getString("ent") + " " + string + " " + bundle.getString("errorMsgHard"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }
    private boolean isStringCorrectHeavy(String string){
        if (string.length() < 3 || string.length()>24)
            return false;
        Boolean charsAreAcceptable = true;

        for (int i = 0; i < string.length(); i++) {
            if (!((string.charAt(i)>='a' && string.charAt(i)<='z') ||
                    (string.charAt(i)>='A' && string.charAt(i)<= 'Z'))){
                charsAreAcceptable = false;
            }
        }
        return charsAreAcceptable;
    }

    private boolean isStringCorrectEasy(String string){
        if (string.length() < 3 || string.length()>24)
            return false;
        return true;
    }

    public void setDarkMode(boolean darkMode) {
        Scene scene = fldEmail.getScene();
        isDarkModeOn = darkMode;
        if (!darkMode) {
            scene.getStylesheets().remove("/css/dark_theme.css");
        }else {
            scene.getStylesheets().add("/css/dark_theme.css");
        }
    }
}
