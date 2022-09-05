package ba.unsa.etf.rpr.TestFX;

import ba.unsa.etf.rpr.Controllers.MainApothecaryController;
import ba.unsa.etf.rpr.Main;
import ba.unsa.etf.rpr.Models.Apothecary;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class ApothecaryTest {
    @Start
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Apothecary firstApothecary = new Apothecary(1,"Apothecary1","Apothecary2","Apothecary3", "Apothecary4",
                100D);
        MainApothecaryController controller = new MainApothecaryController(firstApothecary);
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/main_apothecary.fxml" ), bundle);

        loader.setController(controller);


        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }


    @Test
    public void apothecaryTest(FxRobot robot) {
        Label label = robot.lookup("#lbTotalProfit").queryAs(Label.class);
        assertEquals("Total profit:", label.getText());
        TextField textField = robot.lookup("#fldTotalProfit").queryAs(TextField.class);
        assertEquals("100.0€", textField.getText());
    }

    @Test
    public void apothecaryTest2(FxRobot robot) {
        robot.clickOn("#btnAddDrug");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#tfNameEng");
        robot.write("a");
        TextField textField = robot.lookup("#tfNameEng").queryAs(TextField.class);

        assertEquals("a", textField.getText());
        assertTrue(textField.getStyleClass().contains("fieldNotCorrect"));
        robot.clickOn("Add");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#tfNameEng");
        robot.write("aa");
        assertTrue(textField.getStyleClass().contains("fieldCorrect"));
        robot.clickOn("#btnExitDrugAdd");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}