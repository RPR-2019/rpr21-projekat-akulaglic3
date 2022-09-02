package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.Models.Apothecary;
import ba.unsa.etf.rpr.Models.Drug;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainApothecaryController {
    public MenuItem actionAbout;
    public MenuItem actionHelp;
    public ListView listDrug;
    public TextField fldNameBosnian;
    public TextField fldNameEnglish;
    public TextField fldNameLatin;
    public TextField fldTotalProfit;
    private Apothecary apothecary;
    private ApothecaryDAO apothecaryDAO;

    public MainApothecaryController(Apothecary apothecary) {
        this.apothecary = apothecary;
    }

    @FXML
    public void initialize() throws SQLException {
        apothecaryDAO= ApothecaryDAO.getInstance();
        listDrug.setItems(apothecaryDAO.getDrugsForApothecary(apothecary));
    }


    public void actionAddNewDrug(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/drug_add.fxml" ), bundle);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        DrugAddController controller = loader.getController();
        controller.initData(apothecary);
        stage.setTitle("eHealth");
        stage.show();
    }

    public void actionClose(ActionEvent actionEvent) {
        System.exit(0);
    }
}
