package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Enums.Allergies;
import ba.unsa.etf.rpr.Models.User;
import ba.unsa.etf.rpr.Utility.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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

    private User currentUser;
    private List<CheckBox> listOfCheckBox;
    private UserDAO userDAO;
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");
    private Validator validator;

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


        validator = Validator.getInstance();

        fldName.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectHeavy(newString)) {
                fldName.getStyleClass().removeAll("fieldNotCorrect");
                fldName.getStyleClass().add("fieldCorrect");
            } else {
                fldName.getStyleClass().removeAll("fieldCorrect");
                fldName.getStyleClass().add("fieldNotCorrect");
            }
        });
        fldSurname.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectHeavy(newString)) {
                fldSurname.getStyleClass().removeAll("fieldNotCorrect");
                fldSurname.getStyleClass().add("fieldCorrect");
            } else {
                fldSurname.getStyleClass().removeAll("fieldCorrect");
                fldSurname.getStyleClass().add("fieldNotCorrect");
            }
        });
        fldDoctorName.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectHeavy(newString)) {
                fldDoctorName.getStyleClass().removeAll("fieldNotCorrect");
                fldDoctorName.getStyleClass().add("fieldCorrect");
            } else {
                fldDoctorName.getStyleClass().removeAll("fieldCorrect");
                fldDoctorName.getStyleClass().add("fieldNotCorrect");
            }
        });
        fldDoctorSurname.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectHeavy(newString)) {
                fldDoctorSurname.getStyleClass().removeAll("fieldNotCorrect");
                fldDoctorSurname.getStyleClass().add("fieldCorrect");
            } else {
                fldDoctorSurname.getStyleClass().removeAll("fieldCorrect");
                fldDoctorSurname.getStyleClass().add("fieldNotCorrect");
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
        fldEmail.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectEasy(newString)) {
                fldEmail.getStyleClass().removeAll("fieldNotCorrect");
                fldEmail.getStyleClass().add("fieldCorrect");
            } else {
                fldEmail.getStyleClass().removeAll("fieldCorrect");
                fldEmail.getStyleClass().add("fieldNotCorrect");
            }
        });
    }

    public EditAccountUserController(User user){
        currentUser = user;
    }

    public void actionSave(ActionEvent actionEvent) {
        String name = fldName.getText();
        String surname = fldSurname.getText();
        String password = fldPassword.getText();
        String doctorName = fldDoctorName.getText(), doctorSurname = fldDoctorSurname.getText();

        if (!validator.isStringCorrectHeavy(name)){
            alertIncorrectHeavy(bundle.getString("nameRU"));
        }else if (!validator.isStringCorrectHeavy(surname)){
            alertIncorrectHeavy(bundle.getString("surnameRU"));
        }else if (!validator.isStringCorrectEasy(password)){
            alertIncorrectEasy(bundle.getString("passwordRU"));
        }else if (!validator.isStringCorrectHeavy(doctorName)){
            alertIncorrectHeavy(bundle.getString("doctor_nameRU"));
        }else if (!validator.isStringCorrectHeavy(doctorSurname)){
            alertIncorrectHeavy(bundle.getString("doctor_surnameRU"));
        }else {
            List<Allergies> allergies = new ArrayList<>();
            if (cbAspirin.isSelected()){
                allergies.add(Allergies.ASPIRIN_ALLERGY);
            }
            if(cbAntibiotics.isSelected()){
                allergies.add(Allergies.ANTIBIOTICS_ALLERGY);
            }
            if(cbNID.isSelected()){
                allergies.add(Allergies.NONSTEROIDAL_ANTI_INFLAMMATORY_ALLERGY);
            }
            if(cbSulfa.isSelected()){
                allergies.add(Allergies.SULFA_DRUGS_ALLERGY);
            }
            if(cbChem.isSelected()){
                allergies.add(Allergies.CHEMOTHERAPY_DRUGS_ALLERGY);
            }
            if(cbInsulin.isSelected()){
                allergies.add(Allergies.INSULIN_ALLERGY);
            }
            if(cbAntiS.isSelected()){
                allergies.add(Allergies.ANTISEIZURE_DRUGS_ALLERGY);
            }
            currentUser.setAllergiesList(allergies);
            userDAO.updateUser(currentUser);
        }
    }

    public void actionBack(ActionEvent actionEvent) {
        Stage stage = (Stage) fldPassword.getScene().getWindow();
        stage.close();
    }


    private void alertIncorrectEasy(String string) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(bundle.getString("invalid") + " " + string + "!");
        errorAlert.setContentText(bundle.getString("ent") + " " + string + " " + bundle.getString("errorMsgEasy"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    private void alertIncorrectHeavy(String string) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(bundle.getString("invalid") + " " + string + "!");
        errorAlert.setContentText(bundle.getString("ent") + " " + string + " " + bundle.getString("errorMsgHard"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    public void setDarkMode(boolean darkMode) {
        Scene scene = fldDoctorSurname.getScene();
        if (!darkMode) {
            scene.getStylesheets().remove("/css/dark_theme.css");
        }else {
            scene.getStylesheets().add("/css/dark_theme.css");
        }
    }
}
