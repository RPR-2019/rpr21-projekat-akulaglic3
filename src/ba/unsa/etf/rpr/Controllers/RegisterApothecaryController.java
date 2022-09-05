package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.Models.Apothecary;
import ba.unsa.etf.rpr.Utility.Validator;
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
    private Validator validator;

    @FXML
    public void initialize() throws SQLException {
        validator = Validator.getInstance();
        apothecaryDAO = ApothecaryDAO.getInstance();

        fldEmail.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectEasy(newString)) {
                fldEmail.getStyleClass().removeAll("fieldNotCorrect");
                fldEmail.getStyleClass().add("fieldCorrect");
            } else {
                fldEmail.getStyleClass().removeAll("fieldCorrect");
                fldEmail.getStyleClass().add("fieldNotCorrect");
            }
        });
        fldAddress.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectEasy(newString)) {
                fldAddress.getStyleClass().removeAll("fieldNotCorrect");
                fldAddress.getStyleClass().add("fieldCorrect");
            } else {
                fldAddress.getStyleClass().removeAll("fieldCorrect");
                fldAddress.getStyleClass().add("fieldNotCorrect");
            }
        });
        fldPassword.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectEasy(newString)) {
                fldPassword.getStyleClass().removeAll("fieldNotCorrect");
                fldPassword.getStyleClass().add("fieldCorrect");
            } else {
                fldPassword.getStyleClass().removeAll("fieldCorrect");
                fldPassword.getStyleClass().add("fieldNotCorrect");
            }
        });



        fldName.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectHeavy(newString)) {
                fldName.getStyleClass().removeAll("fieldNotCorrect");
                fldName.getStyleClass().add("fieldCorrect");
            } else {
                fldName.getStyleClass().removeAll("fieldCorrect");
                fldName.getStyleClass().add("fieldNotCorrect");
            }
        });

        fldPhone.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isPhoneCorrect(newString)) {
                fldPhone.getStyleClass().removeAll("fieldNotCorrect");
                fldPhone.getStyleClass().add("fieldCorrect");
            } else {
                fldPhone.getStyleClass().removeAll("fieldCorrect");
                fldPhone.getStyleClass().add("fieldNotCorrect");
            }
        });
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
        String name = fldName.getText();
        String password = fldPassword.getText(), address = fldAddress.getText();
        String phone = fldPhone.getText();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        if (!validator.isStringCorrectHeavy(name)){
            alertIncorrectHeavy(bundle.getString("nameRU"));
        }else if (!validator.isStringCorrectEasy(password)){
            alertIncorrectEasy(bundle.getString("passwordRU"));
        }else if (!validator.isStringCorrectEasy(fldEmail.getText())){
            alertIncorrectEasy(bundle.getString("emailRU"));
        }else if (!validator.isStringCorrectEasy(address)){
            alertIncorrectEasy(bundle.getString("addressRU"));
        }else if (!validator.isPhoneCorrect(phone)){
            alertIncorrectPhone();
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

    private void alertIncorrectPhone() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        errorAlert.setHeaderText(bundle.getString("incorrectPhoneHeader"));
        errorAlert.setContentText(bundle.getString("incorrectPhoneContent"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }


    private void alertIncorrectEasy(String string) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        errorAlert.setHeaderText(bundle.getString("invalid") + " " + string + "!");
        errorAlert.setContentText(bundle.getString("ent") + " " + string + " " + bundle.getString("errorMsgEasy"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    private void alertIncorrectHeavy(String string) {

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        errorAlert.setHeaderText(bundle.getString("invalid") + " " + string + "!");
        errorAlert.setContentText(bundle.getString("ent") + " " + string + " " + bundle.getString("errorMsgHard"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
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
