package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.Models.Apothecary;
import ba.unsa.etf.rpr.Models.Drug;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainApothecaryController {

    public ListView<Drug> listDrug;
    public TextField fldNameBosnian;
    public TextField fldNameEnglish;
    public TextField fldNameLatin;
    public TextField fldTotalProfit;
    
    public Menu menuFile;
    public MenuItem menuAddDrug;
    public MenuItem menuLogout;
    public MenuItem menuExit;
    public Menu menuHelp;
    public MenuItem menuAbout;
    public MenuItem menuKeyboard;
    public MenuItem menuItemHelp;
    public Menu menuLanguage;
    public Menu menuTheme;
    public MenuItem menuDark;
    public MenuItem menuLight;

    public Label lbTotalProfit;
    public Label lbNameBos;
    public Label lbNameEng;
    public Label lbNameLat;

    public Button btnEditDrug;
    public Button btnDeleteDrug;
    public Button btnAddDrug;
    public Button btnLogout;
    public Button btnExit;


    private ResourceBundle bundle;
    private Apothecary apothecary;
    private ApothecaryDAO apothecaryDAO;
    private boolean isDarkModeOn = false;
    private final String DARK_THEME = "/css/dark_theme.css";
    private final String E_HEALTH = "eHealth";

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
        controller.setDarkMode(isDarkModeOn);
        stage.setTitle(E_HEALTH);

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
        myStage.setTitle(E_HEALTH);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        StartController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);

        Stage stage = (Stage) fldTotalProfit.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionAbout(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/about.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle(E_HEALTH);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        AboutController controller = loader.getController();
        controller.setDarkMode(isDarkModeOn);
        myStage.showAndWait();
    }

    public void actionKeyboardShortcut(ActionEvent actionEvent) {
        File file = new File("resources/pdf/Keyboard Shortcuts PDF.pdf");
        try {
            openFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openFile(File file) throws Exception {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void actionHelp(ActionEvent actionEvent) {
        File file = null;
        if (Locale.getDefault().equals(new Locale("bs", "BA"))){
            file = new File("resources/pdf/Help Guide - English.pdf");
        }else{
            file = new File("resources/pdf/Help Guide - Bosanski.pdf");
        }

        try {
            openFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionEditDrug(ActionEvent actionEvent) throws IOException {
        DrugEditController controller = new DrugEditController((Drug) listDrug.getSelectionModel().getSelectedItem());
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/drug_edit.fxml" ), bundle);

        loader.setController(controller);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        controller.setDarkMode(isDarkModeOn);
        stage.setOnHiding( windowEvent -> {
            listDrug.setItems(apothecaryDAO.getDrugsForApothecary(apothecary));
            listDrug.refresh();
        });

        stage.setTitle(E_HEALTH);
        stage.showAndWait();
    }

    public void actionDeleteDrug(ActionEvent actionEvent) {
        Drug selectedDrug = (Drug) listDrug.getSelectionModel().getSelectedItem();
        apothecaryDAO.deleteDrug(selectedDrug);
        listDrug.setItems(apothecaryDAO.getDrugsForApothecary(apothecary));
        listDrug.refresh();
    }


    private void changeCurrentLabels(){
        lbNameBos.setText(bundle.getString("nameBos"));
        lbNameEng.setText(bundle.getString("nameEng"));
        lbNameLat.setText(bundle.getString("nameLat"));
        lbTotalProfit.setText(bundle.getString("totalProfit"));

        btnAddDrug.setText(bundle.getString("addDrug"));
        btnDeleteDrug.setText(bundle.getString("deleteDrug"));
        btnEditDrug.setText(bundle.getString("editDrug"));
        btnLogout.setText(bundle.getString("logout"));
        btnExit.setText(bundle.getString("exit"));

        //menuFile.setText(bundle.getString("file"));
        menuAbout.setText(bundle.getString("about"));
        menuAddDrug.setText(bundle.getString("addDrug"));
        menuExit.setText(bundle.getString("exit"));

        //menuHelp.setText(bundle.getString("help"));
        menuLogout.setText(bundle.getString("logout"));
        menuItemHelp.setText(bundle.getString("help"));
        menuKeyboard.setText(bundle.getString("keyboardShortcuts"));
        menuLanguage.setText(bundle.getString("language"));
        menuTheme.setText(bundle.getString("theme"));
        menuLight.setText(bundle.getString("themeLight"));
        menuDark.setText(bundle.getString("themeDark"));
    }

    public void actionBosanski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        bundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
        changeCurrentLabels();
    }

    public void actionEngleski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "US"));
        bundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
        changeCurrentLabels();
    }

    public void actionLightTheme(ActionEvent actionEvent) {
        isDarkModeOn = false;
        Scene scene = fldTotalProfit.getScene();
        scene.getStylesheets().remove(DARK_THEME);
    }
    public void actionDarkTheme(ActionEvent actionEvent) {
        Scene scene = fldTotalProfit.getScene();
        scene.getStylesheets().add(DARK_THEME);
        isDarkModeOn = true;
    }

    public void setDarkMode(boolean darkMode) {
        Scene scene = fldNameLatin.getScene();
        isDarkModeOn = darkMode;
        if (!darkMode) {
            scene.getStylesheets().remove(DARK_THEME);
        }else {
            scene.getStylesheets().add(DARK_THEME);
        }
    }
}
