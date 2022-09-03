package ba.unsa.etf.rpr.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class StartController {
    public Label lbChoose;
    public Label lbRegMsg;
    public Label lbAccTypeMsg;
    public Label lbHeadline;
    public Button btnUserAccount;
    public Button btnApotAcc;
    public Button btnCreateAcc;
    public Button btnExit;
    @FXML
    private ImageView idImage;
    public ChoiceBox cbLanguage;

    private ResourceBundle bundle;

    private ObservableList<String> listOfLanguages = FXCollections.observableArrayList();
    @FXML
    public void initialize(){
        idImage.setImage(new Image("\\images\\Logo.png"));
        listOfLanguages.add("Bosanski");
        listOfLanguages.add("English");
        cbLanguage.setItems(listOfLanguages);

        cbLanguage.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue!= null && oldValue.equals(newValue)){
                return;
            }

            if (newValue.equals("Bosanski")) {
                Locale.setDefault(new Locale("bs", "BA"));
                bundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
                changeCurrentLabels();
            }else {
                Locale.setDefault(new Locale("en", "US"));
                bundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
                changeCurrentLabels();
            }
        });


        if (Locale.getDefault().equals(new Locale("en","US"))){
            cbLanguage.setValue(listOfLanguages.get(1));
        }else{
            cbLanguage.setValue(listOfLanguages.get(0));
        }

    }


    private void changeCurrentLabels(){
        lbHeadline.setText(bundle.getString("headline"));
        lbAccTypeMsg.setText(bundle.getString("accountTypeMsg"));
        lbChoose.setText(bundle.getString("lgChoose"));
        lbRegMsg.setText(bundle.getString("regMsg"));

        btnUserAccount.setText(bundle.getString("userAcc"));
        btnApotAcc.setText(bundle.getString("apotAcc"));
        btnCreateAcc.setText(bundle.getString("createAcc"));
        btnExit.setText(bundle.getString("exit"));
    }

    public void actionOpenLoginUser(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/login_user.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        myStage.show();

    }

    public void actionOpenALoginpothecary(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/login_apothecary.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionRegister(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/option.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("eHealth");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        myStage.show();
    }

    public void actionExit(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
