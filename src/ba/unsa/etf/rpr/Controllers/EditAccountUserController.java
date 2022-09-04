package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Enums.Allergies;
import ba.unsa.etf.rpr.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditAccountUserController {
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

    private User currentUser = null;
    private List<CheckBox> listOfCheckBox;
    private UserDAO userDAO;

    @FXML
    void initialize() throws SQLException {
        listOfCheckBox = List.of(cbAspirin, cbAntibiotics, cbNID, cbSulfa, cbChem, cbInsulin, cbAntiS);
        userDAO = UserDAO.getInstance();
        fldName.textProperty().bindBidirectional(currentUser.nameProperty());
        fldSurname.textProperty().bindBidirectional(currentUser.surnameProperty());
        fldUsername.textProperty().bindBidirectional(currentUser.usernameProperty());
        fldEmail.textProperty().bindBidirectional(currentUser.eMailProperty());
        fldDoctorName.textProperty().bindBidirectional(currentUser.doctorNameProperty());
        fldDoctorSurname.textProperty().bindBidirectional(currentUser.doctorSurnameProperty());
        fldPassword.textProperty().bindBidirectional(currentUser.passwordProperty());

        fldUsername.setEditable(false);
        fldUsername.getStyleClass().add("field-grey");
        for (Allergies allergies:currentUser.getAllergiesList()) {
            listOfCheckBox.get(allergies.value).setSelected(true);
        }
    }

    public EditAccountUserController(User user){
        currentUser = user;
    }

    public void actionSave(ActionEvent actionEvent) {
        String name = fldName.getText();
        String surname = fldSurname.getText();
        String password = fldPassword.getText(), username = fldUsername.getText();
        String doctorName = fldDoctorName.getText(), doctorSurname = fldDoctorSurname.getText();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        if (!isStringCorrectEasy(name)){
            alertIncorrectHeavy(bundle.getString("nameRU"));
        }else if (!isStringCorrectHeavy(surname)){
            alertIncorrectHeavy(surname);
        }else if (!isStringCorrectHeavy(bundle.getString("surnameRU"))){
            alertIncorrectHeavy(surname);
        }else if (!isStringCorrectEasy(password)){
            alertIncorrectEasy(bundle.getString("passwordRU"));
        }else if (!isStringCorrectHeavy(doctorName)){
            alertIncorrectHeavy(bundle.getString("doctor_nameRU"));
        }else if (!isStringCorrectHeavy(doctorSurname)){
            alertIncorrectHeavy(bundle.getString("doctor_surnameRU"));
        }else {
            List<Allergies> allergies = new ArrayList<>();
            if (cbAspirin.isSelected()){
                allergies.add(Allergies.aspirinAllergy);
            }
            if(cbAntibiotics.isSelected()){
                allergies.add(Allergies.antibioticsAllergy);
            }
            if(cbNID.isSelected()){
                allergies.add(Allergies.nonsteroidalAntiInflammatoryAllergy);
            }
            if(cbSulfa.isSelected()){
                allergies.add(Allergies.sulfaDrugsAllergy);
            }
            if(cbChem.isSelected()){
                allergies.add(Allergies.chemotherapyDrugsAllergy);
            }
            if(cbInsulin.isSelected()){
                allergies.add(Allergies.insulinAllergy);
            }
            if(cbAntiS.isSelected()){
                allergies.add(Allergies.antiseizureDrugsAllergy);
            }
            currentUser.setAllergiesList(allergies);
            userDAO.updateUser(currentUser);
        }
    }

    public void actionBack(ActionEvent actionEvent) {
        Stage stage = (Stage) fldPassword.getScene().getWindow();
        stage.close();
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
}
