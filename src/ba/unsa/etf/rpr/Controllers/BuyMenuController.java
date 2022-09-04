package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Models.Drug;
import ba.unsa.etf.rpr.Models.Item;
import ba.unsa.etf.rpr.Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class BuyMenuController {
    public TextField fldSearch;
    public ListView listSearchView;
    public ImageView imageCurrent;
    public TextField fldNameEng;
    public TextField fldNameBos;
    public TextField fldNameLat;
    public TextArea fldContent;
    public TextArea fldPurpose;
    public TextField fldDate;
    public TextField fldPrice;
    public TextField fldAmount;
    public Button btnAddToCheckout;
    public Button btnClear;
    public TextField fldType;

    private User currentUser = null;
    private ObservableList<Drug> allDrugs;
    private ObservableList<Drug> searchedDrugs = FXCollections.observableArrayList();
    private ApothecaryDAO apothecaryDAO;
    private UserDAO userDAO;
    private ObservableList<String> listOfAdministrationTypes = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        apothecaryDAO = ApothecaryDAO.getInstance();
        allDrugs = apothecaryDAO.getAllDrugs();
        userDAO = UserDAO.getInstance();
        btnAddToCheckout.setDisable(true);
        btnClear.setDisable(true);
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
                fldDate.setText("");
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

                fldDate.setText(newDrug.getExpirationDate().toString());
                fldType.setText(listOfAdministrationTypes.get(newDrug.getAdministrationTypes().value));

                Image imageDrug = null;
                Blob blob = null;
                try {
                    blob = new SerialBlob(newDrug.getPictureUrl());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                InputStream inputStream = null;
                try {
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
        searchedDrugs.clear();
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

        searchedDrugs = searchedDrugs.stream().distinct().collect(Collectors.collectingAndThen(toList(), list -> FXCollections.observableArrayList(list)));
        listSearchView.setItems(searchedDrugs);
        listSearchView.refresh();
    }

    public void actionAddToCheckout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        alert.setTitle(bundle.getString("confirmationTitle"));
        alert.setHeaderText(bundle.getString("confirmationTitle"));
        alert.setContentText(bundle.getString("confirmationContent"));
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Optional<ButtonType> result = alert.showAndWait();
        if (fldAmount.getText().equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("errorNoAmountHeader"));
            errorAlert.setContentText(bundle.getString("errorNoAmountContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else if (fldAmount.getText().equals("0")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("errorInvalidAmountHeader"));
            errorAlert.setContentText(bundle.getString("errorInvalidAmountContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }
        else if(result.isPresent() || result.get() == ButtonType.OK) {
            Item newItem = new Item(0, (Drug) listSearchView.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(fldAmount.getText()),currentUser.getId());
            userDAO.addItem(newItem);
            actionClear(actionEvent);
        }
    }

    public void actionClear(ActionEvent actionEvent) {
        fldNameEng.setText("");
        fldNameLat.setText("");
        fldNameBos.setText("");
        fldAmount.setText("");
        fldContent.setText("");
        fldPrice.setText("");
        fldType.setText("");
        fldPurpose.setText("");
        fldDate.setText("");
        imageCurrent.setImage(null);
        btnAddToCheckout.setDisable(true);
        btnClear.setDisable(true);
        listSearchView.getSelectionModel().clearSelection();
        listSearchView.setItems(allDrugs);
    }

    public void actionExit(ActionEvent actionEvent) {
        Stage stage = (Stage) fldAmount.getScene().getWindow();
        stage.close();
    }
}
