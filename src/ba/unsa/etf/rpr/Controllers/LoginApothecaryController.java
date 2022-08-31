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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginApothecaryController {
    public TextField fldApothecaryName;
    public PasswordField fldPassword;
    private ApothecaryDAO apothecaryDAO;

    @FXML
    public void initialize() throws SQLException {
        apothecaryDAO = ApothecaryDAO.getInstance();
    }

    public void actionLogin(ActionEvent actionEvent) throws IOException {
        Boolean userExists = apothecaryDAO.checkIfAdminExists(fldApothecaryName.getCharacters().toString(), fldPassword.getCharacters().toString());

        if(userExists){
            Stage myStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainApothecary.fxml"));
            myStage.setTitle("eHealth");
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
            myStage.show();
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Apothecary name and password don't match");
            errorAlert.setContentText("The entered name and password don't match. Are you sure you already have an account?");
            errorAlert.showAndWait();
        }
    }

    public void actionBack(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start.fxml"));
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        myStage.show();
    }
}
