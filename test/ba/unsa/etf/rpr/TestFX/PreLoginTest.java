package ba.unsa.etf.rpr.TestFX;

import ba.unsa.etf.rpr.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PreLoginTest {
    @Start
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent mainNode = FXMLLoader.load(Main.class.getResource("/fxml/start.fxml"),bundle);
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }


    @Test
    public void preLoginTest1(FxRobot robot) {
        robot.clickOn("#menuView");
        robot.clickOn("#menuLanguage");
        robot.clickOn("English");
        Label label = robot.lookup("#lbHeadline").queryAs(Label.class);
        assertEquals("Welcome to eHealth", label.getText());
    }

    @Test
    public void preLoginTest2(FxRobot robot) {
        robot.clickOn("Create an account");
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#btnApothecary");
        robot.clickOn("#fldName");
        robot.write("a");
        TextField textField = robot.lookup("#fldName").queryAs(TextField.class);

        assertEquals("a", textField.getText());
        assertTrue(textField.getStyleClass().contains("fieldNotCorrect"));
        robot.clickOn("Register");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        try {
            Thread.sleep(200); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#fldName");
        robot.write("aa");
        assertTrue(textField.getStyleClass().contains("fieldCorrect"));
        robot.clickOn("Back");
    }


    @Test
    public void preLoginTest3(FxRobot robot) {
        robot.clickOn("Create an account");
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#btnUser");
        robot.clickOn("#fldName");
        robot.write("b");
        TextField textField = robot.lookup("#fldName").queryAs(TextField.class);

        assertEquals("b", textField.getText());
        assertTrue(textField.getStyleClass().contains("fieldNotCorrect"));
        robot.clickOn("Register");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        try {
            Thread.sleep(200); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#fldName");
        robot.write("aa");
        assertTrue(textField.getStyleClass().contains("fieldCorrect"));
        robot.clickOn("Back");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#btnApotAcc");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#fldApothecaryName");
        robot.write("My username is");
        TextField textField3 = robot.lookup("#fldApothecaryName").queryAs(TextField.class);

        assertEquals("My username is", textField3.getText());
        robot.clickOn("#fldPassword");
        robot.write("My password is");
        TextField textField2 = robot.lookup("#fldPassword").queryAs(TextField.class);

        assertEquals("My password is", textField2.getText());
        robot.clickOn("Login");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.clickOn("Back");
        robot.clickOn("Exit");
    }

}