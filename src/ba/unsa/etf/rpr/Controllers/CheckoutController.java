package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAO.ApothecaryDAO;
import ba.unsa.etf.rpr.Jasper.Report;
import ba.unsa.etf.rpr.Utility.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class CheckoutController {
    public ToggleGroup paymentGroup;
    public RadioButton rbCreditCard;
    public RadioButton rbDelivery;
    public TextField fldPhone;
    public TextField fldAddress;
    public TextField fldCreditCard;
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");

    private int buyerId;
    private final String STYLE_CONSTANT = "field-grey";
    private Boolean confirmed = false;
    private Validator validator;
    @FXML
    void initialize(){
        validator = Validator.getInstance();
        fldAddress.setText("");
        fldAddress.setEditable(false);
        fldPhone.setText("");
        fldPhone.setEditable(false);
        fldCreditCard.setEditable(false);
        fldCreditCard.setText("");
        fldAddress.getStyleClass().add(STYLE_CONSTANT);
        fldPhone.getStyleClass().add(STYLE_CONSTANT);
        fldCreditCard.getStyleClass().add(STYLE_CONSTANT);

        paymentGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (t1.equals(rbCreditCard)){
                fldAddress.setText("");
                fldAddress.setEditable(false);
                fldPhone.setText("");
                fldPhone.setEditable(false);
                fldAddress.getStyleClass().add(STYLE_CONSTANT);
                fldPhone.getStyleClass().add(STYLE_CONSTANT);

                fldCreditCard.setEditable(true);
                fldCreditCard.getStyleClass().removeIf(s -> s.equals(STYLE_CONSTANT));
            }else{
                fldAddress.setEditable(true);
                fldPhone.setEditable(true);
                fldPhone.getStyleClass().removeIf(s -> s.equals(STYLE_CONSTANT));
                fldAddress.getStyleClass().removeIf(s -> s.equals(STYLE_CONSTANT));

                fldCreditCard.setEditable(false);
                fldCreditCard.setText("");
                fldCreditCard.getStyleClass().add(STYLE_CONSTANT);
            }
        });
    }

    public void setDarkMode(boolean darkMode) {
        Scene scene = fldCreditCard.getScene();
        if (!darkMode) {
            scene.getStylesheets().remove("/css/dark_theme.css");
        }else {
            scene.getStylesheets().add("/css/dark_theme.css");
        }
    }

    public void actionConfirm(ActionEvent actionEvent) {
        if (!rbDelivery.isSelected() && !rbCreditCard.isSelected()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("noPaymentPlanSelectionHeader"));
            errorAlert.setContentText(bundle.getString("noPaymentPlanSelectionContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else if (rbCreditCard.isSelected() && fldCreditCard.getText().equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("enterCreditCardHeader"));
            errorAlert.setContentText(bundle.getString("enterCreditCardContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else if (rbDelivery.isSelected() && (fldPhone.getText().equals("") || fldAddress.getText().equals(""))){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(bundle.getString("enterPAHeader"));
            errorAlert.setContentText(bundle.getString("enterPAContent"));
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }else{
        confirmed = true;
        Stage stage = (Stage) fldAddress.getScene().getWindow();
        stage.close();
        }
    }

    public void actionCancel(ActionEvent actionEvent) {
        confirmed = false;
        Stage stage = (Stage) fldAddress.getScene().getWindow();
        stage.close();
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void actionPrintReport(ActionEvent actionEvent) {
        try {
            new Report(buyerId).showReport(ApothecaryDAO.getInstance().getConnection());
        } catch (JRException | SQLException e1) {
            e1.printStackTrace();
        }
    }

    void initBuyer(int idOfBuyer){
        buyerId = idOfBuyer;
    }
}
