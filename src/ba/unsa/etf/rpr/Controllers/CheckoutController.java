package ba.unsa.etf.rpr.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class CheckoutController {
    public ToggleGroup paymentGroup;
    public RadioButton rbCreditCard;
    public RadioButton rbDelivery;
    public TextField fldPhone;
    public TextField fldAddress;
    public TextField fldCreditCard;

    private Boolean confirmed = false;

    @FXML
    void initialize(){
        fldAddress.setText("");
        fldAddress.setEditable(false);
        fldPhone.setText("");
        fldPhone.setEditable(false);
        fldCreditCard.setEditable(false);
        fldCreditCard.setText("");
        fldAddress.getStyleClass().add("field-grey");
        fldPhone.getStyleClass().add("field-grey");
        fldCreditCard.getStyleClass().add("field-grey");

        paymentGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (t1.equals(rbCreditCard)){
                fldAddress.setText("");
                fldAddress.setEditable(false);
                fldPhone.setText("");
                fldPhone.setEditable(false);
                fldAddress.getStyleClass().add("field-grey");
                fldPhone.getStyleClass().add("field-grey");

                fldCreditCard.setEditable(true);
                fldCreditCard.getStyleClass().removeIf(s -> {
                    return s.equals("field-grey");
                });
            }else{
                fldAddress.setEditable(true);
                fldPhone.setEditable(true);
                fldPhone.getStyleClass().removeIf(s -> {
                    return s.equals("field-grey");
                });
                fldAddress.getStyleClass().removeIf(s -> {
                    return s.equals("field-grey");
                });

                fldCreditCard.setEditable(false);
                fldCreditCard.setText("");
                fldCreditCard.getStyleClass().add("field-grey");
            }
        });


        /*rbCreditCard.setOnAction(actionEvent -> {
            if (rbCreditCard.isSelected()){

            }
        });
        rbDelivery.setOnAction(actionEvent -> {
            if (rbCreditCard.isSelected()){

            }
        });*/
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
        confirmed = true;
        Stage stage = (Stage) fldAddress.getScene().getWindow();
        stage.close();
    }

    public void actionCancel(ActionEvent actionEvent) {
        confirmed = false;
        Stage stage = (Stage) fldAddress.getScene().getWindow();
        stage.close();
    }

    public Boolean getConfirmed() {
        return confirmed;
    }
}
