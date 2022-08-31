package ba.unsa.etf.rpr.Models;

import ba.unsa.etf.rpr.Enums.AdministrationTypes;
import javafx.beans.property.*;
import java.sql.Date;
import java.util.Objects;

public class Drug {
    private SimpleStringProperty nameBosnian, nameEnglish, nameLatin, content, purpose;
    private SimpleObjectProperty<Date> expirationDate;
    private AdministrationTypes administrationTypes;
    private String pictureUrl;
    private SimpleDoubleProperty price;
    private Apothecary apothecary;

    public Drug(String nameBosnian, String nameEnglish, String nameLatin, String content, String purpose
            ,Date expirationDate, AdministrationTypes administrationTypes, String pictureUrl, Double price,
                Apothecary apothecary) {
        this.nameBosnian = new SimpleStringProperty(nameBosnian);
        this.nameEnglish = new SimpleStringProperty(nameEnglish);
        this.nameLatin = new SimpleStringProperty(nameLatin);
        this.content = new SimpleStringProperty(content);
        this.purpose = new SimpleStringProperty(purpose);
        this.expirationDate = new SimpleObjectProperty<Date>(expirationDate);
        this.administrationTypes = administrationTypes;
        this.pictureUrl = pictureUrl;
        this.price = new SimpleDoubleProperty(price);
        this.apothecary = apothecary;
    }

    public Drug() {
        this("","","","","",new Date(1,1,1), AdministrationTypes.oralAdministration,"", 0D, new Apothecary());
    }

    public Apothecary getApothecary() {
        return apothecary;
    }

    public void setApothecary(Apothecary apothecary) {
        this.apothecary = apothecary;
    }

    public String getNameBosnian() {
        return nameBosnian.get();
    }

    public SimpleStringProperty nameBosnianProperty() {
        return nameBosnian;
    }

    public void setNameBosnian(String nameBosnian) {
        this.nameBosnian.set(nameBosnian);
    }

    public String getNameEnglish() {
        return nameEnglish.get();
    }

    public SimpleStringProperty nameEnglishProperty() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish.set(nameEnglish);
    }

    public String getNameLatin() {
        return nameLatin.get();
    }

    public SimpleStringProperty nameLatinProperty() {
        return nameLatin;
    }

    public void setNameLatin(String nameLatin) {
        this.nameLatin.set(nameLatin);
    }

    public String getContent() {
        return content.get();
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getPurpose() {
        return purpose.get();
    }

    public SimpleStringProperty purposeProperty() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose.set(purpose);
    }

    public Date getExpirationDate() {
        return expirationDate.get();
    }

    public ObjectProperty<Date> expirationDateProperty() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate.set(expirationDate);
    }

    public AdministrationTypes getAdministrationTypes() {
        return administrationTypes;
    }

    public void setAdministrationTypes(AdministrationTypes administrationTypes) {
        this.administrationTypes = administrationTypes;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }


    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drug)) return false;
        Drug drug = (Drug) o;
        return Objects.equals(nameBosnian, drug.nameBosnian) && Objects.equals(nameEnglish, drug.nameEnglish) && Objects.equals(nameLatin, drug.nameLatin) && Objects.equals(content, drug.content) && Objects.equals(purpose, drug.purpose) && Objects.equals(expirationDate, drug.expirationDate) && administrationTypes == drug.administrationTypes && Objects.equals(pictureUrl, drug.pictureUrl) && Objects.equals(price, drug.price) && Objects.equals(apothecary, drug.apothecary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameBosnian, nameEnglish, nameLatin, content, purpose, expirationDate, administrationTypes, pictureUrl, price, apothecary);
    }
}
