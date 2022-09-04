package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Enums.AdministrationTypes;
import ba.unsa.etf.rpr.Exceptions.IllegalAdministrationType;
import ba.unsa.etf.rpr.Models.Admin;
import ba.unsa.etf.rpr.Models.Apothecary;
import ba.unsa.etf.rpr.Models.Drug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApothecaryDAO {
    private static ApothecaryDAO instance;
    private Connection connection;
    private PreparedStatement testStatmentQuery, getAdminByNameAndPassword, getAdminByUsernameQuery
            , insertNewAdminQuery, getIdForNewApothecary, updateProfitQuery, insertNewApothecaryQuery, getApothecaryQuery, getApothecaryByIDQuery;

    private PreparedStatement insertDrugQuery, getDrugsForApothecaryQuery, deleteDrugQuery,
     updateDrugQuery, getDrugByIdQuery, getAllDrugsQuery;

    private ApothecaryDAO() throws SQLException {
        String url = "jdbc:sqlite:eHealthDatabase.db";
        connection = DriverManager.getConnection(url);

        try{
            testStatmentQuery = connection.prepareStatement("Select * from drug");
        }catch (SQLException e){
            createDatabase();
            testStatmentQuery = connection.prepareStatement("Select * from drug");
        }

        getAdminByNameAndPassword =
                connection.prepareStatement("Select * from Admin Where apothecary_name = ? and password = ?");
        getAdminByUsernameQuery =
                connection.prepareStatement("Select * from Admin Where apothecary_name = ?");
        getApothecaryQuery =
                connection.prepareStatement("Select * from Apothecary Where name = ?");
        getApothecaryByIDQuery =
                connection.prepareStatement("Select * from Apothecary Where id = ?");

        insertNewApothecaryQuery =
                connection.prepareStatement("INSERT INTO Apothecary VALUES ((SELECT MAX(a.id) FROM apothecary a)+1,?,?,?,?,?)");
        insertNewAdminQuery =
                connection.prepareStatement("INSERT INTO Admin VALUES ((SELECT MAX(a.id) FROM admin a)+1,?,?)");
        getIdForNewApothecary =
                connection.prepareStatement("SELECT MAX(a.id)+1 FROM apothecary a");
        updateProfitQuery = connection.prepareStatement("UPDATE Apothecary SET total_profit=? WHERE id=?");

        insertDrugQuery =connection.prepareStatement("INSERT INTO Drug VALUES ((SELECT MAX(d.id) FROM drug d)+1,?,?,?,?,?,?,?,?,?, ?)");
        getDrugsForApothecaryQuery = connection.prepareStatement("Select * from Drug where apothecary_id=? ORDER BY name_english");
        deleteDrugQuery = connection.prepareStatement("Delete from Drug where id = ?");
        updateDrugQuery = connection.prepareStatement("UPDATE Drug SET name_bosnian=?, name_english=?, name_latin=? " +
                ", content=?, purpose=?, expiration_date=?, administration_type=?, picture=?, price=? WHERE id=?");

        getDrugByIdQuery = connection.prepareStatement("SELECT * FROM DRUG where id = ?");
        getAllDrugsQuery = connection.prepareStatement("Select * FROM drug");
    }

    public static void removeInstance() throws SQLException {
        if (instance == null) return;
        instance.connection.close();
        instance = null;
    }

    private void createDatabase() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("eHealthDatabase.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka... nastavljam sa praznom bazom");
        }
    }

    public static ApothecaryDAO getInstance() throws SQLException {
        if (instance == null){
            instance = new ApothecaryDAO();
        }
        return instance;
    }

    private List<Admin> getAdminListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Admin> listOfAdmins = new ArrayList<>();
        while (resultSet.next()){
            listOfAdmins.add(new Admin(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3)));
        }

        return listOfAdmins;
    }
    private List<Apothecary> getApothecaryListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Apothecary> listOfApothecary = new ArrayList<>();
        while (resultSet.next()){
            listOfApothecary.add(new Apothecary(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                    resultSet.getDouble(6)));
        }

        return listOfApothecary;
    }

    public Boolean checkIfAdminExists(String name, String password) {
        try {
            getAdminByNameAndPassword.setString(1, name);
            getAdminByNameAndPassword.setString(2, password);
            ResultSet resultSet = getAdminByNameAndPassword.executeQuery();
            List<Admin> adminList = getAdminListFromResultSet(resultSet);
            if (adminList.size()==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean checkForSameAdminName(String name) {
        try {
            getAdminByUsernameQuery.setString(1, name);
            ResultSet resultSet = getAdminByUsernameQuery.executeQuery();

            List<Admin> adminList = getAdminListFromResultSet(resultSet);
            if (adminList.size()==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addApothecary(String name, String email, String password,
                              String address, String phone) {
        try {
            insertNewApothecaryQuery.setString(1,name);
            insertNewApothecaryQuery.setString(2,email);
            insertNewApothecaryQuery.setString(3,address);
            insertNewApothecaryQuery.setString(4,phone);
            insertNewApothecaryQuery.setDouble(5,0);
            insertNewApothecaryQuery.execute();

            insertNewAdminQuery.setString(1, name);
            insertNewAdminQuery.setString(2, password);
            insertNewAdminQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDrug(String nameBos, String nameEng, String nameLat,
                        String purpose, String content, String expDate, int selectedIndex, byte[] imageDrug, Double price, int idApothecary) {
        try {
            insertDrugQuery.setString(1,nameBos);
            insertDrugQuery.setString(2,nameEng);
            insertDrugQuery.setString(3,nameLat);
            insertDrugQuery.setString(4,content);
            insertDrugQuery.setString(5,purpose);
            insertDrugQuery.setString(6,expDate);
            insertDrugQuery.setInt(7,selectedIndex);
            insertDrugQuery.setBytes(8,imageDrug);
            insertDrugQuery.setDouble(9, price);
            insertDrugQuery.setInt(10,idApothecary);
            insertDrugQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Apothecary getApothecary(String name) {
        List<Apothecary> apothecaryList = new ArrayList<>();
        try {
            getApothecaryQuery.setString(1,name);
            ResultSet resultSet = getApothecaryQuery.executeQuery();
            apothecaryList = getApothecaryListFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (apothecaryList.size()==0){
            return null;
        }
        return apothecaryList.get(0);
    }

    public Apothecary getApothecaryByID(int id) {
        List<Apothecary> apothecaryList = new ArrayList<>();
        try {
            getApothecaryByIDQuery.setInt(1, id);
            ResultSet resultSet = getApothecaryByIDQuery.executeQuery();
            apothecaryList = getApothecaryListFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (apothecaryList.size()==0){
            return null;
        }
        return apothecaryList.get(0);
    }

    public ObservableList<Drug> getDrugsForApothecary(Apothecary apothecary){
        ObservableList<Drug> drugList = FXCollections.observableArrayList();
        try {
            getDrugsForApothecaryQuery.setInt(1, apothecary.getId());
            ResultSet resultSet = getDrugsForApothecaryQuery.executeQuery();
            drugList = getDrugsFromRS(resultSet, apothecary);
        } catch (SQLException | IllegalAdministrationType e) {
            e.printStackTrace();
        }
        return drugList;
    }

    private ObservableList<Drug> getDrugsFromRS(ResultSet resultSet, Apothecary apothecary) throws SQLException, IllegalAdministrationType {
        ObservableList<Drug> drugList = FXCollections.observableArrayList();
        while (resultSet.next()){
            LocalDate date=null;
            String dateString = resultSet.getString(7);
            byte[] pictureBytes = resultSet.getBytes(9);

            date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);

            drugList.add(new Drug(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(6), date, AdministrationTypes.valueOf(resultSet.getInt(8)),
                    pictureBytes, resultSet.getDouble(10), apothecary));
        }

        return drugList;
    }

    public void deleteDrug(Drug selectedDrug) {
        try {
            deleteDrugQuery.setInt(1, selectedDrug.getId());
            deleteDrugQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDrug(Drug currentDrug) {
        try {
            updateDrugQuery.setString(1, currentDrug.getNameBosnian());
            updateDrugQuery.setString(2, currentDrug.getNameEnglish());
            updateDrugQuery.setString(3, currentDrug.getNameLatin());
            updateDrugQuery.setString(4, currentDrug.getContent());
            updateDrugQuery.setString(5, currentDrug.getPurpose());
            updateDrugQuery.setString(6, currentDrug.getExpirationDate().toString());
            updateDrugQuery.setInt(7, currentDrug.getAdministrationTypes().value);
            updateDrugQuery.setBytes(8, currentDrug.getPictureUrl());
            updateDrugQuery.setDouble(9, currentDrug.getPrice());
            updateDrugQuery.setInt(10, currentDrug.getId());
            updateDrugQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Drug getDrugById(int anInt) {
        ObservableList<Drug> drugObservableList = FXCollections.observableArrayList();
        try {
            getDrugByIdQuery.setInt(1, anInt);
            ResultSet resultSet = getDrugByIdQuery.executeQuery();

            Apothecary apothecary = getApothecaryByID(resultSet.getInt(11));
            drugObservableList = getDrugsFromRS(resultSet, apothecary);
        } catch (SQLException | IllegalAdministrationType e) {
            e.printStackTrace();
        }

        return drugObservableList.get(0);
    }

    public ObservableList<Drug> getAllDrugs() {
        ObservableList<Drug> drugList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = getAllDrugsQuery.executeQuery();
            drugList = getAllDrugsFromRS(resultSet);
        } catch (SQLException | IllegalAdministrationType e) {
            e.printStackTrace();
        }
        return drugList;
    }

    private ObservableList<Drug> getAllDrugsFromRS(ResultSet resultSet) throws SQLException, IllegalAdministrationType {
        ObservableList<Drug> drugList = FXCollections.observableArrayList();
        while (resultSet.next()){
            LocalDate date=null;
            String dateString = resultSet.getString(7);
            byte[] pictureBytes = resultSet.getBytes(9);
            Apothecary apothecary = ApothecaryDAO.getInstance().getApothecaryByID(resultSet.getInt(11));

            date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);

            drugList.add(new Drug(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(6), date, AdministrationTypes.valueOf(resultSet.getInt(8)),
                    pictureBytes, resultSet.getDouble(10), apothecary));
        }

        return drugList;
    }

    public void addProfit(Apothecary apothecary, double v) {
        Double newTotalProfit = apothecary.getTotalProfit()+v;

        try {
            updateProfitQuery.setDouble(1, newTotalProfit);
            updateProfitQuery.setInt(2, apothecary.getId());
            updateProfitQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
