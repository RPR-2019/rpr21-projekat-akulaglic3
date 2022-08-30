package ba.unsa.etf.rpr.Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Apothecary {
    private SimpleStringProperty name, adress, contactPhone, email;
    private Admin admin;
    private SimpleDoubleProperty totalProfit;

    public Apothecary(String name, String adress, String contactPhone,
                      String email, Admin admin, Double totalProfit) {
        this.name = new SimpleStringProperty(name);
        this.adress = new SimpleStringProperty(adress);
        this.contactPhone = new SimpleStringProperty(contactPhone);
        this.email = new SimpleStringProperty(email);
        this.admin = admin;
        this.totalProfit = new SimpleDoubleProperty(totalProfit);
    }

    public Apothecary() {
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAdress() {
        return adress.get();
    }

    public SimpleStringProperty adressProperty() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress.set(adress);
    }

    public String getContactPhone() {
        return contactPhone.get();
    }

    public SimpleStringProperty contactPhoneProperty() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone.set(contactPhone);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public double getTotalProfit() {
        return totalProfit.get();
    }

    public SimpleDoubleProperty totalProfitProperty() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit.set(totalProfit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apothecary)) return false;
        Apothecary that = (Apothecary) o;
        return Objects.equals(name, that.name) && Objects.equals(adress, that.adress) && Objects.equals(contactPhone, that.contactPhone) && Objects.equals(email, that.email) && Objects.equals(admin, that.admin) && Objects.equals(totalProfit, that.totalProfit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, adress, contactPhone, email, admin, totalProfit);
    }
}
