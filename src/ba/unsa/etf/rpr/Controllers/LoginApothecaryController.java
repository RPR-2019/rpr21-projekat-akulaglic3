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

public class LoginApothecaryController {
    public TextField fldApothecaryName;
    public PasswordField fldPassword;
    private ApothecaryDAO apothecaryDAO;
    private boolean isDarkModeOn = false;

    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");


    @FXML
    public void initialize() throws SQLException {
        apothecaryDAO = ApothecaryDAO.getInstance();
    }

    public void actionLogin(ActionEvent actionEvent) throws IOException {
        Boolean userExists = apothecaryDAO.checkIfAdminExists(fldApothecaryName.getCharacters().toString(), fldPassword.getCharacters().toString());

        if(userExists){
            Apothecary apothecary = apothecaryDAO.getApothecary(fldApothecaryName.getText());

            MainApothecaryController controller = new MainApothecaryController(apothecary);
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
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("errorLoginHeadline2"));
            errorAlert.setContentText(bundle.getString("errorLoginContent2"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }
    }

    public void actionBack(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
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

    public void setDarkMode(boolean darkMode) {
        Scene scene = fldApothecaryName.getScene();
        isDarkModeOn = darkMode;
        if (!darkMode) {
            scene.getStylesheets().remove("/css/dark_theme.css");
        }else {
            scene.getStylesheets().add("/css/dark_theme.css");
        }
    }
}
