package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*String url = "jdbc:sqlite:baza.db";
        Connection connection = DriverManager.getConnection(url);
        Statement stat = connection.createStatement();
        ResultSet resultSet = stat.executeQuery("Select * from knjiga");
*/

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start.fxml"));
        primaryStage.setTitle("eHealth");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

