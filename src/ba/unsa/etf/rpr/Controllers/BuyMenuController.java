package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Models.Drug;
import ba.unsa.etf.rpr.Models.Item;
import ba.unsa.etf.rpr.Models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sql.rowset.serial.SerialBlob;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class BuyMenuController {
    public TextField fldSearch;
    public ListView listSearchView;
    public ImageView imageCurrent;
    public TextField fldNameEng;
    public TextField fldNameBos;
    public TextField fldNameLat;
    public TextArea fldContent;
    public TextArea fldPurpose;
    public DatePicker dpDatePicker;
    public TextField fldPrice;
    public TextField fldAmount;
    public Button btnAddToCheckout;
    public Button btnClear;
    public TextField cbType;

    private User currentUser = null;
    private ObservableList<Drug> allDrugs;
    private ObservableList<Drug> searchedDrugs;
    private ApothecaryDAO apothecaryDAO;
    private UserDAO userDAO;
    private ObservableList<String> listOfAdministrationTypes = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        apothecaryDAO = ApothecaryDAO.getInstance();
        allDrugs = apothecaryDAO.getAllDrugs();
        userDAO = UserDAO.getInstance();

        fldAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fldAmount.setText(newValue.replaceAll("[^\\d]", ""));
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

        listSearchView.setItems(allDrugs);
        listSearchView.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem == null){
                fldNameEng.setText("");
                fldNameLat.setText("");
                fldNameBos.setText("");
                fldAmount.setText("");
                fldContent.setText("");
                fldPrice.setText("");
                fldPurpose.setText("");
                dpDatePicker.setValue(null);
                imageCurrent.setImage(null);
                btnAddToCheckout.setDisable(true);
                btnClear.setDisable(true);
            }else {
                Drug newDrug = (Drug) newItem;
                fldNameEng.setText(newDrug.getNameEnglish());
                fldNameLat.setText(newDrug.getNameLatin());
                fldNameBos.setText(newDrug.getNameBosnian());
                fldAmount.setText("");
                fldContent.setText(newDrug.getContent());
                fldPrice.setText(Double.valueOf(newDrug.getPrice()).toString());
                fldPurpose.setText(newDrug.getPurpose());

                dpDatePicker.setValue(newDrug.getExpirationDate());
                cbType.setText(listOfAdministrationTypes.get(newDrug.getAdministrationTypes().value));

                Image imageDrug = null;
                InputStream inputStream = null;
                Blob blob = null;
                try {
                    blob = new SerialBlob(newDrug.getPictureUrl());
                    inputStream = blob.getBinaryStream();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                imageDrug = new Image(inputStream);
                imageCurrent.setImage(imageDrug);

                btnAddToCheckout.setDisable(false);
                btnClear.setDisable(false);
            }
        });
    }

    public void initUser(User user){
        currentUser = user;
    }

    public void actionSearch(ActionEvent actionEvent) {
        actionClear(actionEvent);
        String enteredText = fldSearch.getText().toLowerCase(Locale.ROOT);
        searchedDrugs.addAll(allDrugs.stream().filter(drug -> {
            Boolean wordContains = drug.getNameEnglish().toLowerCase(Locale.ROOT).contains(enteredText);
            return wordContains;
        }).toList());
        searchedDrugs.addAll(allDrugs.stream().filter(drug -> {
            Boolean wordContains = drug.getNameBosnian().toLowerCase(Locale.ROOT).contains(enteredText);
            return wordContains;
        }).toList());
        searchedDrugs.addAll(allDrugs.stream().filter(drug -> {
            Boolean wordContains = drug.getNameLatin().toLowerCase(Locale.ROOT).contains(enteredText);
            return wordContains;
        }).toList());

        searchedDrugs = (ObservableList<Drug>) searchedDrugs.stream().distinct().toList();
        listSearchView.setItems(searchedDrugs);
        listSearchView.refresh();
    }

    public void actionAddToCheckout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        alert.setTitle(bundle.getString("confirmationTitle"));
        alert.setContentText("confirmationContent");

        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent() || result.get() != ButtonType.OK) {
            Item newItem = new Item(0, (Drug) listSearchView.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(fldAmount.getText()),currentUser.getId());
            userDAO.addItem(newItem);
        }
    }

    public void actionClear(ActionEvent actionEvent) {
        fldNameEng.setText("");
        fldNameLat.setText("");
        fldNameBos.setText("");
        fldAmount.setText("");
        fldContent.setText("");
        fldPrice.setText("");
        fldPurpose.setText("");
        dpDatePicker.setValue(null);
        imageCurrent.setImage(null);
        btnAddToCheckout.setDisable(true);
        btnClear.setDisable(true);
        listSearchView.getSelectionModel().clearSelection();
        listSearchView.setItems(allDrugs);
    }

    public void actionExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
