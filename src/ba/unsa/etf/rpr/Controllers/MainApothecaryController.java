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
import javafx.scene.control.Button;
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
    public Button btnEditDrug;
    public Button btnDeleteDrug;
    private Apothecary apothecary;
    private ApothecaryDAO apothecaryDAO;

    public MainApothecaryController(Apothecary apothecary) {
        this.apothecary = apothecary;
    }

    @FXML
    public void initialize() throws SQLException {
        apothecaryDAO= ApothecaryDAO.getInstance();
        btnEditDrug.setDisable(true);
        btnDeleteDrug.setDisable(true);
        fldTotalProfit.setEditable(false);
        fldNameEnglish.setEditable(false);
        fldNameBosnian.setEditable(false);
        fldNameLatin.setEditable(false);

        fldTotalProfit.setText(apothecary.getTotalProfit() + "€");

        listDrug.setItems(apothecaryDAO.getDrugsForApothecary(apothecary));
        listDrug.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {

            if (oldItem != null){
                Drug oldDrug = (Drug) oldItem;
                fldNameBosnian.textProperty().unbindBidirectional(oldDrug.nameBosnianProperty());
                fldNameEnglish.textProperty().unbindBidirectional(oldDrug.nameEnglishProperty());
                fldNameLatin.textProperty().unbindBidirectional(oldDrug.nameLatinProperty());
            }

            if (newItem == null){
                btnEditDrug.setDisable(true);
                btnDeleteDrug.setDisable(true);
                fldNameLatin.setText("");
                fldNameBosnian.setText("");
                fldNameEnglish.setText("");
            }else {
                btnEditDrug.setDisable(false);
                btnDeleteDrug.setDisable(false);
                Drug newDrug = (Drug) newItem;
                fldNameBosnian.textProperty().bindBidirectional(newDrug.nameBosnianProperty());
                fldNameEnglish.textProperty().bindBidirectional(newDrug.nameEnglishProperty());
                fldNameLatin.textProperty().bindBidirectional(newDrug.nameLatinProperty());
            }
        });
    }


    public void actionAddNewDrug(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/drug_add.fxml" ), bundle);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        DrugAddController controller = loader.getController();
        controller.initData(apothecary);
        stage.setTitle("eHealth");

        stage.setOnHiding( windowEvent -> {
            listDrug.setItems(apothecaryDAO.getDrugsForApothecary(apothecary));
            listDrug.refresh();
        });
        stage.showAndWait();
    }

    public void actionClose(ActionEvent actionEvent) {
        System.exit(0);
    }


    public void actionLogout(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/start.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        ;
        Stage stage = (Stage) fldTotalProfit.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionAbout(ActionEvent actionEvent) {
    }

    public void actionKeyboardShortcut(ActionEvent actionEvent) {
    }

    public void actionHelp(ActionEvent actionEvent) {
    }

    public void actionEditDrug(ActionEvent actionEvent) throws IOException {
        DrugEditController controller = new DrugEditController((Drug) listDrug.getSelectionModel().getSelectedItem());
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/drug_edit.fxml" ), bundle);

        loader.setController(controller);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        stage.setOnHiding( windowEvent -> {
            listDrug.setItems(apothecaryDAO.getDrugsForApothecary(apothecary));
            listDrug.refresh();
        });

        stage.setTitle("eHealth");
        stage.showAndWait();
    }

    public void actionDeleteDrug(ActionEvent actionEvent) {
        Drug selectedDrug = (Drug) listDrug.getSelectionModel().getSelectedItem();
        apothecaryDAO.deleteDrug(selectedDrug);
        listDrug.setItems(apothecaryDAO.getDrugsForApothecary(apothecary));
        listDrug.refresh();
    }
}
