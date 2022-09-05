package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.Models.Apothecary;
import ba.unsa.etf.rpr.Utility.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

public class DrugAddController {
    public TextField tfNameEng;
    public TextField tfNameBos;
    public TextField tfNameLat;
    public TextArea taContent;
    public TextArea taPurpose;
    public DatePicker dpExpDate;
    public TextField tfPrice;
    public Button btnUpload;
    public ImageView idImageDrugs;
    public ChoiceBox cmbAdministrationType;
    private ApothecaryDAO apothecaryDAO;

    private ObservableList<String> listOfAdministrationTypes = FXCollections.observableArrayList();
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");
    private Apothecary apothecary;
    private byte[] imageOfDrug;
    private Validator validator;

    @FXML
    public void initialize() throws SQLException {
        apothecaryDAO = ApothecaryDAO.getInstance();
        idImageDrugs.getStyleClass().add("image-view-wrapper");

        tfPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                tfPrice.setText(oldValue);
            }
        });

        if (Locale.getDefault().equals(new Locale("bs", "BA"))){
            listOfAdministrationTypes.add("Oralno");
            listOfAdministrationTypes.add("Sublingvalno");
            listOfAdministrationTypes.add("Rektalno");
            listOfAdministrationTypes.add("Parenteralno");
        }else{
            listOfAdministrationTypes.add("Oral");
            listOfAdministrationTypes.add("Sublingual");
            listOfAdministrationTypes.add("Rectal");
            listOfAdministrationTypes.add("Parenteral");
        }
        cmbAdministrationType.setItems(listOfAdministrationTypes);


        validator = Validator.getInstance();

        tfNameBos.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectHeavy(newString)) {
                tfNameBos.getStyleClass().removeAll("fieldNotCorrect");
                tfNameBos.getStyleClass().add("fieldCorrect");
            } else {
                tfNameBos.getStyleClass().removeAll("fieldCorrect");
                tfNameBos.getStyleClass().add("fieldNotCorrect");
            }
        });
        tfNameLat.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectHeavy(newString)) {
                tfNameLat.getStyleClass().removeAll("fieldNotCorrect");
                tfNameLat.getStyleClass().add("fieldCorrect");
            } else {
                tfNameLat.getStyleClass().removeAll("fieldCorrect");
                tfNameLat.getStyleClass().add("fieldNotCorrect");
            }
        });
        tfNameEng.textProperty().addListener((obs, oldString, newString) -> {
            if (validator.isStringCorrectHeavy(newString)) {
                tfNameEng.getStyleClass().removeAll("fieldNotCorrect");
                tfNameEng.getStyleClass().add("fieldCorrect");
            } else {
                tfNameEng.getStyleClass().removeAll("fieldCorrect");
                tfNameEng.getStyleClass().add("fieldNotCorrect");
            }
        });
    }

    @FXML
    public void actionUpload(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG
                    = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
            FileChooser.ExtensionFilter extFilterjpg
                    = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter extFilterPNG
                    = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
            FileChooser.ExtensionFilter extFilterpng
                    = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
            fileChooser.getExtensionFilters()
                    .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);

            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                WritableImage image = toFXImage(bufferedImage, null);
                idImageDrugs.setImage(image);
                idImageDrugs.setFitWidth(200);
                idImageDrugs.setFitHeight(200);
                idImageDrugs.scaleXProperty();
                idImageDrugs.scaleYProperty();
                idImageDrugs.setSmooth(true);
                idImageDrugs.setCache(true);
                FileInputStream fin = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];

                for (int readNum; (readNum = fin.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
                imageOfDrug = bos.toByteArray();
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger("ss");
            }
        }

    public void actionClear(ActionEvent actionEvent) {
        idImageDrugs.setImage(null);
        imageOfDrug = null;
    }

    public void actionAddDrug(ActionEvent actionEvent) {
        String nameBos = tfNameBos.getText(), nameLat = tfNameLat.getText(), nameEng = tfNameEng.getText();
        String content = taContent.getText(), purpose = taPurpose.getText();
        LocalDate localDate = dpExpDate.getValue();



        if (!validator.isStringCorrectHeavy(nameBos)){
            alertIncorrectHeavy(bundle.getString("nameBOSRU"));
        }else if (!validator.isStringCorrectHeavy(nameEng)){
            alertIncorrectHeavy(bundle.getString("nameENGRU"));
        }else if (!validator.isStringCorrectHeavy(nameLat)){
            alertIncorrectHeavy(bundle.getString("nameLATRU"));
        }else if(localDate == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("invalidDateHeader"));
            errorAlert.setContentText(bundle.getString("invalidDateContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }
        else if(Objects.equals(tfPrice.getText(), "")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("invalidPriceHeader"));
            errorAlert.setContentText(bundle.getString("invalidPriceContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else if(imageOfDrug == null || imageOfDrug.length == 0){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("invalidImageHeader"));
            errorAlert.setContentText(bundle.getString("invalidImageContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else if(cmbAdministrationType.getSelectionModel().getSelectedItem()==null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("invalidBoxHeader"));
            errorAlert.setContentText(bundle.getString("invalidBoxContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else {
            Double price = Double.parseDouble(tfPrice.getText());
            apothecaryDAO.addDrug(nameBos, nameEng, nameLat,
                    purpose, content, localDate.toString(),
                    cmbAdministrationType.getSelectionModel().getSelectedIndex(), imageOfDrug,price, apothecary.getId());
            Stage stage = (Stage) tfNameLat.getScene().getWindow();
            stage.close();

        }
    }

    public void actionExit(ActionEvent actionEvent) {
        Stage stage = (Stage) tfNameLat.getScene().getWindow();
        stage.close();
    }

    private void alertIncorrectEasy(String string) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(bundle.getString("invalid") + " " + string + "!");
        errorAlert.setContentText(bundle.getString("ent") + " " + string + " " + bundle.getString("errorMsgEasy"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    private void alertIncorrectHeavy(String string) {

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(bundle.getString("invalid") + " " + string + "!");
        errorAlert.setContentText(bundle.getString("ent") + " " + string + " " + bundle.getString("errorMsgHard"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    public void initData(Apothecary apothecary) {
        this.apothecary = apothecary;
    }

    public void setDarkMode(boolean darkMode) {
        Scene scene = tfPrice.getScene();
        if (!darkMode) {
            scene.getStylesheets().remove("/css/dark_theme.css");
        }else {
            scene.getStylesheets().add("/css/dark_theme.css");
        }
    }
}
