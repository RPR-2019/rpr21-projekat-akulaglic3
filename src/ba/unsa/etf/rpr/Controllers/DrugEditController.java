package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.Enums.AdministrationTypes;
import ba.unsa.etf.rpr.Exceptions.IllegalAdministrationType;
import ba.unsa.etf.rpr.Models.Drug;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

public class DrugEditController {
    public TextField tfNameEng;
    public TextField tfNameBos;
    public TextField tfNameLat;
    public TextArea taContent;
    public TextArea taPurpose;
    public DatePicker dpExpDate;
    public ImageView idImageDrugs;
    public Button btnUpload;
    public TextField tfPrice;
    public ChoiceBox cmbAdministrationType;


    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");
    private byte[] currentByteArray;
    private ObservableList<String> listOfAdministrationTypes = FXCollections.observableArrayList();
    private ApothecaryDAO apothecaryDao;

    private Drug currentDrug;
    @FXML
    public void initialize() throws SQLException {
        apothecaryDao = ApothecaryDAO.getInstance();
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

        tfNameBos.textProperty().bindBidirectional(currentDrug.nameBosnianProperty());
        tfNameEng.textProperty().bindBidirectional(currentDrug.nameEnglishProperty());
        tfNameLat.textProperty().bindBidirectional(currentDrug.nameLatinProperty());
        taContent.textProperty().bindBidirectional(currentDrug.contentProperty());
        taPurpose.textProperty().bindBidirectional(currentDrug.purposeProperty());
        tfPrice.textProperty().bindBidirectional(new SimpleStringProperty(currentDrug.priceProperty().getValue().toString()));


        dpExpDate.setValue(currentDrug.getExpirationDate());
        cmbAdministrationType.getSelectionModel().select(currentDrug.getAdministrationTypes().value);

        Image imageDrug = null;
        Blob blob = new SerialBlob(currentDrug.getPictureUrl());
        InputStream inputStream = blob.getBinaryStream();
        imageDrug = new Image(inputStream);
        idImageDrugs.setImage(imageDrug);

        currentByteArray = currentDrug.getPictureUrl();
    }

    public DrugEditController(Drug drug){
        currentDrug = drug;
    }

    public void actionSaveDrug(ActionEvent actionEvent) throws IllegalAdministrationType {
        if (!isStringCorrectHeavy(currentDrug.getNameBosnian())){
            alertIncorrectHeavy(bundle.getString("nameBOSRU"));
        }else if (!isStringCorrectHeavy(currentDrug.getNameEnglish())){
            alertIncorrectHeavy(bundle.getString("nameENGRU"));
        }else if (!isStringCorrectHeavy(currentDrug.getNameLatin())){
            alertIncorrectHeavy(bundle.getString("nameLATRU"));
        }else {
            currentDrug.setAdministrationTypes(AdministrationTypes.valueOf(cmbAdministrationType.getSelectionModel().getSelectedIndex()));
            currentDrug.setExpirationDate(dpExpDate.getValue());
            currentDrug.setPictureUrl(currentByteArray);
            currentDrug.setPrice(Double.parseDouble(tfPrice.getText()));

            apothecaryDao.updateDrug(currentDrug);

            Stage stage = (Stage) tfNameLat.getScene().getWindow();
            stage.close();

        }
    }

    public void actionExit(ActionEvent actionEvent) {
        Stage stage = (Stage) tfNameLat.getScene().getWindow();
        stage.close();
    }

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
            currentByteArray = bos.toByteArray();
            currentDrug.setPictureUrl(bos.toByteArray());

            fin.close();
        } catch (IOException ex) {
            Logger.getLogger("ss");
        }
    }

    public void actionClear(ActionEvent actionEvent) {
        idImageDrugs.setImage(null);
        currentByteArray = null;
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
    private boolean isStringCorrectHeavy(String string){
        if (string.length() < 3 || string.length()>24)
            return false;
        Boolean charsAreAcceptable = true;

        for (int i = 0; i < string.length(); i++) {
            if (!((string.charAt(i)>='a' && string.charAt(i)<='z') ||
                    (string.charAt(i)>='A' && string.charAt(i)<= 'Z'))){
                charsAreAcceptable = false;
            }
        }
        return charsAreAcceptable;
    }

    private boolean isStringCorrectEasy(String string){
        if (string.length() < 3 || string.length()>24)
            return false;
        return true;
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
