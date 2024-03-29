package ba.unsa.etf.rpr.Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Apothecary {
    private int id;
    private SimpleStringProperty name, address, contactPhone, email;
    private SimpleDoubleProperty totalProfit;

    public Apothecary(int id, String name, String adress, String contactPhone,
                      String email, Double totalProfit) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(adress);
        this.contactPhone = new SimpleStringProperty(contactPhone);
        this.email = new SimpleStringProperty(email);
        this.totalProfit = new SimpleDoubleProperty(totalProfit);
    }

    public Apothecary() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
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
        return Objects.equals(name.get(), that.name.get()) &&
                Objects.equals(address.get(), that.address.get())
                && Objects.equals(contactPhone.get(), that.contactPhone.get()) &&
                Objects.equals(email.get(), that.email.get())
                && Objects.equals(totalProfit.get(), that.totalProfit.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, contactPhone, email, totalProfit);
    }
}
