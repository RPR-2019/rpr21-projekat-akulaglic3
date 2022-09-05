package ba.unsa.etf.rpr.TestFX;

import ba.unsa.etf.rpr.Controllers.MainApothecaryController;
import ba.unsa.etf.rpr.Controllers.MainUserController;
import ba.unsa.etf.rpr.Enums.Allergies;
import ba.unsa.etf.rpr.Main;
import ba.unsa.etf.rpr.Models.Apothecary;
import ba.unsa.etf.rpr.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class UserTest {
    @Start
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        User firstUser= new User(1,"user","user","user","user","user",
                "user","user", List.of(Allergies.ASPIRIN_ALLERGY));
        MainUserController controller = new MainUserController(firstUser);
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/main_user.fxml" ), bundle);

        loader.setController(controller);


        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }


    @Test
    public void apothecaryTest(FxRobot robot) {
        Label label = robot.lookup("#lbWelcome").queryAs(Label.class);
        assertEquals("Welcome user", label.getText());
        robot.clickOn("#btnEditAcc");
        robot.clickOn("#fldName");
        robot.write("Best");
        robot.clickOn("Save");
        robot.clickOn("Back");
        assertEquals("Welcome user", label.getText());
    }

   @Test
    public void apothecaryTest2(FxRobot robot) {
        robot.clickOn("#btnBuyMenu");
        try {
            Thread.sleep(500); // Slobodno stavite više ako ne bude dovoljno
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       TextField textField = robot.lookup("#fldSearch").queryAs(TextField.class);

        assertEquals("", textField.getText());
        robot.clickOn("#fldSearch");
        robot.write("text");
        Button button = robot.lookup("#btnSearch").queryAs(Button.class);
        robot.clickOn("#btnSearch");
        assertEquals("Search", button.getText());

        robot.clickOn("Exit");
    }
}