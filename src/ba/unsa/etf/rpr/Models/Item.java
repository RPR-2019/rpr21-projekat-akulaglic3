package ba.unsa.etf.rpr.Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Item {
    private Drug drug;
    private SimpleIntegerProperty amount;
    private SimpleDoubleProperty totalPrice;

    public Item(Drug drug, Integer amount, Double totalPrice) {
        this.drug = drug;
        this.amount = new SimpleIntegerProperty(amount);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
    }

    public Item() {
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public int getAmount() {
        return amount.get();
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public SimpleDoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(drug, item.drug) && Objects.equals(amount, item.amount) && Objects.equals(totalPrice, item.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drug, amount, totalPrice);
    }
}
