package ba.unsa.etf.rpr.Models;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.Objects;

public class Item {
    private int id;
    private Drug drug;
    private SimpleIntegerProperty amount;
    private int buyerId;

    public Item(int id, Drug drug, Integer amount, int buyerId) {
        this.id = id;
        this.drug = drug;
        this.amount = new SimpleIntegerProperty(amount);
        this.buyerId = buyerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(drug, item.drug) && Objects.equals(amount.get(), item.amount.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(drug, amount);
    }

    @Override
    public String toString() {
        return drug.getNameEnglish() + ", " + drug.getNameLatin() + "/ Price = " + drug.getPrice() +
                ", Amount = " + amount.get() + ", Total = " + (amount.get()*drug.getPrice());
    }
}
