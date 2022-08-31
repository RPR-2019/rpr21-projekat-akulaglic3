package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
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

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionRegister(ActionEvent actionEvent) {
        String name = fldName.getCharacters().toString();
        String password = fldPassword.getText(), address = fldAddress.getCharacters().toString();
        String phone = fldPhone.getCharacters().toString();

        if (!isStringCorrectHeavy(name)){
            alertIncorectHeavy("name");
        }else if (!isStringCorrectEasy(password)){
            alertIncorrectEasy("password");
        }else if (!isStringCorrectEasy(address)){
            alertIncorrectEasy("address");
        }else if (!isStringCorrectEasy(phone)){
            alertIncorrectEasy("contact phone");
        }else if (apothecaryDAO.checkForSameName(name)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Name already taken!");
            errorAlert.setContentText("The entered name of an apothecary is already being used. Please use a different one!");
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else {
            apothecaryDAO.addApothecary(name, fldEmail.toString(),
                    password, address, phone);
        }
    }


    private void alertIncorrectEasy(String string) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Invalid " + string + "!");
        errorAlert.setContentText("The entered " + string + " is invalid. It has to be between 3 and 24 characters!");
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    private void alertIncorectHeavy(String string) {

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Invalid " + string + "!");
        errorAlert.setContentText("The entered " + string + " is invalid. It has to be between 3 and 24 characters " +
                "and contain only letters A-Z and a-z!");
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
}
