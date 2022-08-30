package ba.unsa.etf.rpr.Models;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class User {
    private SimpleStringProperty name, surname,username, eMail, password, doctorName, doctorSurname;
    private List<Allergies> allergiesList;

    public User() {
    }

    public User(String name, String surname, String username, String eMail, String password,
                String doctorName, String doctorSurname, List<Allergies> allergiesList) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.username = new SimpleStringProperty(username);
        this.eMail = new SimpleStringProperty(eMail);
        this.password = new SimpleStringProperty(password);
        this.doctorName = new SimpleStringProperty(doctorName);
        this.doctorSurname = new SimpleStringProperty(doctorSurname);
        this.allergiesList = allergiesList;
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

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String geteMail() {
        return eMail.get();
    }

    public SimpleStringProperty eMailProperty() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail.set(eMail);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getDoctorName() {
        return doctorName.get();
    }

    public SimpleStringProperty doctorNameProperty() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName.set(doctorName);
    }

    public String getDoctorSurname() {
        return doctorSurname.get();
    }

    public SimpleStringProperty doctorSurnameProperty() {
        return doctorSurname;
    }

    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname.set(doctorSurname);
    }

    public List<Allergies> getAllergiesList() {
        return allergiesList;
    }

    public void setAllergiesList(List<Allergies> allergiesList) {
        this.allergiesList = allergiesList;
    }
}
