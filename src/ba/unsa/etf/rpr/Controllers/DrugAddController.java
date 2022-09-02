package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.Models.Apothecary;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private ApothecaryDAO apothecaryDAO = null;

    private Apothecary apothecary = null;
    private byte[] person_image = null;
    @FXML
    public void initialize() throws SQLException {
        apothecaryDAO = ApothecaryDAO.getInstance();
        idImageDrugs.getStyleClass().add("image-view-wrapper");

        /*tfPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfPrice.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });*/
        tfPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    tfPrice.setText(oldValue);
                }
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
                person_image = bos.toByteArray();

            } catch (IOException ex) {
                Logger.getLogger("ss");
            }
        }

    public void actionClear(ActionEvent actionEvent) {
        idImageDrugs.setImage(null);
        person_image = null;
    }

    public void actionAddDrug(ActionEvent actionEvent) {
        String nameBos = tfNameBos.getText(), nameLat = tfNameLat.getText(), nameEng = tfNameEng.getText();
        String content = taContent.getText(), purpose = taPurpose.getText();
        LocalDate localDate = dpExpDate.getValue();
        Double price = Double.parseDouble(tfPrice.getText());
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        if (!isStringCorrectHeavy(nameBos)){
            alertIncorrectHeavy(bundle.getString("nameBOSRU"));
        }else if (!isStringCorrectHeavy(nameEng)){
            alertIncorrectHeavy(bundle.getString("nameENGRU"));
        }else if (!isStringCorrectHeavy(nameLat)){
            alertIncorrectHeavy(bundle.getString("nameLATRU"));
        }else {
            apothecaryDAO.addDrug(nameBos, nameEng, nameLat,
                    purpose, content, localDate.toString(), person_image,price, apothecary.getId());
        }
    }

    public void actionExit(ActionEvent actionEvent) {
        Stage stage = (Stage) tfNameLat.getScene().getWindow();
        stage.close();
    }

    private void alertIncorrectEasy(String string) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        errorAlert.setHeaderText(bundle.getString("invalid") + " " + string + "!");
        errorAlert.setContentText(bundle.getString("ent") + " " + string + " " + bundle.getString("errorMsgEasy"));
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    private void alertIncorrectHeavy(String string) {

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
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

    public void initData(Apothecary apothecary) {
        this.apothecary = apothecary;
    }
}
