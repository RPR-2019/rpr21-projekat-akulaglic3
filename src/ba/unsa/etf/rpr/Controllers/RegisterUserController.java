package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class RegisterUserController {
    public TextField fldName;
    public TextField fldSurname;
    public PasswordField fldPassword;
    public TextField fldDoctorName;
    public TextField fldEmail;
    public TextField fldUsername;
    public TextField fldDoctorSurname;

    public CheckBox cbAspirin;
    public CheckBox cbAntibiotics;
    public CheckBox cbNID;
    public CheckBox cbSulfa;
    public CheckBox cbChem;
    public CheckBox cbInsulin;
    public CheckBox cbAntiS;

    private UserDAO userDAO;

    @FXML
    public void initialize() throws SQLException {
        userDAO = UserDAO.getInstance();
    }

    public void actionRegister(ActionEvent actionEvent) {
        String name = fldName.getText();
        String surname = fldSurname.getText();
        String password = fldPassword.getText(), username = fldUsername.getText();
        String doctorName = fldDoctorName.getText(), doctorSurname = fldDoctorSurname.getText();

        if (!isStringCorrectEasy(name)){
            alertIncorrectHeavy("name");
        }else if (!isStringCorrectHeavy(surname)){
            alertIncorrectHeavy(surname);
        }else if (!isStringCorrectHeavy("surname")){
            alertIncorrectHeavy(surname);
        }else if (!isStringCorrectEasy(username)){
            alertIncorrectEasy("username");
        }else if (!isStringCorrectEasy(password)){
            alertIncorrectEasy("password");
        }else if (!isStringCorrectHeavy(doctorName)){
            alertIncorrectHeavy("doctor name");
        }else if (!isStringCorrectHeavy(doctorSurname)){
            alertIncorrectHeavy("doctor surname");
        }else if (userDAO.checkForSameUsername(username)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Username taken!");
            errorAlert.setContentText("The entered username is already being used. Please use a different one!");
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else {
            List<Integer> allergies = new ArrayList<>();
            if (cbAspirin.isSelected()){
                allergies.add(0);
            }
            if(cbAntibiotics.isSelected()){
                allergies.add(1);
            }
            if(cbNID.isSelected()){
                allergies.add(2);
            }
            if(cbSulfa.isSelected()){
                allergies.add(3);
            }
            if(cbChem.isSelected()){
                allergies.add(4);
            }
            if(cbInsulin.isSelected()){
                allergies.add(5);
            }
            if(cbAntiS.isSelected()){
                allergies.add(6);
            }
            userDAO.addUser(name, surname, username, fldEmail.getText(), password, doctorName, doctorSurname,allergies);
        }
    }

    private void alertIncorrectEasy(String string) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Invalid " + string + "!");
        errorAlert.setContentText("The entered " + string + " is invalid. It has to be between 3 and 24 characters!");
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    private void alertIncorrectHeavy(String string) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Invalid " + string + "!");
        errorAlert.setContentText("The entered " + string + " is invalid. It has to be between 3 and 24 characters " +
                "and contain only letters A-Z and a-z!");
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
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
