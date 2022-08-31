package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Models.Admin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApothecaryDAO {
    private static ApothecaryDAO instance = null;
    private Connection connection;
    private PreparedStatement preparedStatement, getAdminByNameAndPassword, getAdminByUsernameQuery
            , insertNewAdminQuery, getIdForNewApothecary, insertNewApothecaryQuery;

    private ApothecaryDAO() throws SQLException {
        String url = "jdbc:sqlite:eHealthDatabase.db";
        connection = DriverManager.getConnection(url);

        try{
            preparedStatement = connection.prepareStatement("Select * from drug");
        }catch (SQLException e){
            createDatabase();
            preparedStatement = connection.prepareStatement("Select * from drug");
        }

        getAdminByNameAndPassword =
                connection.prepareStatement("Select * from Admin Where apothecary_name = ? and password = ?");
        getAdminByUsernameQuery =
                connection.prepareStatement("Select * from Admin Where apothecary_name = ?");

        insertNewApothecaryQuery =
                connection.prepareStatement("INSERT INTO Apothecary VALUES ((SELECT MAX(a.id) FROM apothecary a)+1,?,?,?,?,?)");
        insertNewAdminQuery =
                connection.prepareStatement("INSERT INTO Admin VALUES ((SELECT MAX(a.id) FROM admin a)+1,?,?)");
        getIdForNewApothecary =
                connection.prepareStatement("SELECT MAX(a.id)+1 FROM apothecary a");
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

    public Boolean checkForSameName(String name) {
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
}
