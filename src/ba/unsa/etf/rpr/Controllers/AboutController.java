package ba.unsa.etf.rpr.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutController {
    public ImageView idImageLogo;
    //private boolean isDarkModeOn;

    @FXML
    public void initialize(){
        idImageLogo.setImage(new Image("\\images\\Logo.png"));
    }

    public void setDarkMode(boolean darkMode) {
        Scene scene = idImageLogo.getScene();
        //isDarkModeOn = darkMode;
        if (!darkMode) {
            scene.getStylesheets().remove("/css/dark_theme.css");
        }else {
            scene.getStylesheets().add("/css/dark_theme.css");
        }
    }
}
