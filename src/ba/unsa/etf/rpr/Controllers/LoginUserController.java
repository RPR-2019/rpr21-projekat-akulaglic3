package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Models.Apothecary;
import ba.unsa.etf.rpr.Models.User;
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

public class LoginUserController {
    public PasswordField fldPassword;
    public TextField fldUsername;
    private UserDAO userDAO;

    @FXML
    public void initialize() throws SQLException {
        userDAO = UserDAO.getInstance();
    }

    public void actionLogin(ActionEvent actionEvent) throws IOException, SQLException {
        Boolean userExists = userDAO.checkIfUserExists(fldUsername.getCharacters().toString(), fldPassword.getCharacters().toString());

        if(userExists){
            User user = userDAO.getUser(fldUsername.getText());

            MainUserController controller = new MainUserController(user);
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/main_user.fxml" ), bundle);

            loader.setController(controller);
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

            stage.setTitle("eHealth");
            stage.show();

            Node n = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) n.getScene().getWindow();
            currentStage.close();
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            errorAlert.setHeaderText(bundle.getString("errorLoginHeadline"));
            errorAlert.setContentText(bundle.getString("errorLoginContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }
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
}
