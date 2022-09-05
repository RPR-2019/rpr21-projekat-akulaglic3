package ba.unsa.etf.rpr.Models;

import ba.unsa.etf.rpr.Enums.AdministrationTypes;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Drug {
    private int id;
    private SimpleStringProperty nameBosnian, nameEnglish, nameLatin, content, purpose;
    private SimpleObjectProperty<LocalDate> expirationDate;
    private AdministrationTypes administrationTypes;
    private byte[] pictureUrl;
    private SimpleDoubleProperty price;
    private Apothecary apothecary;

    public Drug(int id, String nameBosnian, String nameEnglish, String nameLatin, String content, String purpose
            ,LocalDate expirationDate, AdministrationTypes administrationTypes, byte[] pictureUrl, Double price,
                Apothecary apothecary) {
        this.id = id;
        this.nameBosnian = new SimpleStringProperty(nameBosnian);
        this.nameEnglish = new SimpleStringProperty(nameEnglish);
        this.nameLatin = new SimpleStringProperty(nameLatin);
        this.content = new SimpleStringProperty(content);
        this.purpose = new SimpleStringProperty(purpose);
        this.expirationDate = new SimpleObjectProperty<LocalDate>(expirationDate);
        this.administrationTypes = administrationTypes;
        this.pictureUrl = pictureUrl;
        this.price = new SimpleDoubleProperty(price);
        this.apothecary = apothecary;
    }

    public Drug() {
    }

    public Apothecary getApothecary() {
        return apothecary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getExpirationDate() {
        return expirationDate.get();
    }

    public ObjectProperty<LocalDate> expirationDateProperty() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate.set(expirationDate);
    }

    public AdministrationTypes getAdministrationTypes() {
        return administrationTypes;
    }

    public void setAdministrationTypes(AdministrationTypes administrationTypes) {
        this.administrationTypes = administrationTypes;
    }

    @Override
    public String toString() {
        return nameEnglish.get() + "(" + nameLatin.get() +
                "), " + price.get() +
                '€';
    }

    public byte[] getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(byte[] pictureUrl) {
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
        return Objects.equals(nameBosnian.get(), drug.nameBosnian.get())
                && Objects.equals(nameEnglish.get(), drug.nameEnglish.get()) &&
                Objects.equals(nameLatin.get(), drug.nameLatin.get()) &&
                Objects.equals(content.get(), drug.content.get()) &&
                Objects.equals(purpose.get(), drug.purpose.get()) &&
                Objects.equals(expirationDate.get(), drug.expirationDate.get()) &&
                administrationTypes == drug.administrationTypes &&
                Arrays.equals(pictureUrl, drug.pictureUrl) &&
                Objects.equals(price.get(), drug.price.get()) &&
                Objects.equals(apothecary, drug.apothecary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameBosnian, nameEnglish, nameLatin, content, purpose, expirationDate, administrationTypes, pictureUrl, price, apothecary);
    }
}
