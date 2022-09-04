package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Models.Drug;
import ba.unsa.etf.rpr.Models.Item;
import ba.unsa.etf.rpr.Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDAO {
    private static UserDAO instance;
    private Connection connection;
    private PreparedStatement testStatementQuery, getUserByUsernameAndPasswordQuery,
            getUserByUsernameQuery, insertNewUserQuery, insertAllergyForUser, getIdForNewUser,updateUserQuery;

    private PreparedStatement getCheckoutItemsForUserQuery, addItemQuery, deleteItemQuery;

    private UserDAO() throws SQLException {
        String url = "jdbc:sqlite:eHealthDatabase.db";
        connection = DriverManager.getConnection(url);

        try{
            testStatementQuery = connection.prepareStatement("Select * from user");
        }catch (SQLException e){
            createDatabase();
            testStatementQuery = connection.prepareStatement("Select * from user");
        }

        getUserByUsernameAndPasswordQuery =
                connection.prepareStatement("Select * from User Where username = ? and password = ?");
        getUserByUsernameQuery =
                connection.prepareStatement("Select * from User Where username = ?");


        insertNewUserQuery =
                connection.prepareStatement("INSERT INTO User VALUES ((SELECT MAX(u.id) FROM user u)+1,?,?,?,?,?,?,?)");
        insertAllergyForUser =
                connection.prepareStatement("INSERT INTO Allergies VALUES ((SELECT MAX(a.id) FROM Allergies a)+1,?,?)");
        getIdForNewUser =
                connection.prepareStatement("SELECT MAX(u.id)+1 FROM user u");
        updateUserQuery = connection.prepareStatement("UPDATE User SET first_name=?, second_name=?, username=? " +
                ", email=?, password=?, doctor_first_name=?, doctor_second_name=? WHERE id=?");


        getCheckoutItemsForUserQuery =  connection.prepareStatement("SELECT * FROM Item WHERE buyer_id = ?");
        addItemQuery =  connection.prepareStatement("INSERT INTO Item VALUES ((SELECT MAX(i.id) FROM item i)+1,?,?,?)");
        deleteItemQuery =  connection.prepareStatement("DELETE FROM Item WHERE id = ?");
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

    public static UserDAO getInstance() throws SQLException {
        if (instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    public Boolean checkIfUserExists(String username, String password) {
        try {
            getUserByUsernameAndPasswordQuery.setString(1, username);
            getUserByUsernameAndPasswordQuery.setString(2, password);
            ResultSet resultSet = getUserByUsernameAndPasswordQuery.executeQuery();

            List<User> listOfUsers = new ArrayList<>();
            while (resultSet.next()){
                listOfUsers.add(new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8), new ArrayList<>()));
            }

            if (listOfUsers.size()==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean checkForSameUsername(String username) {
        try {
            getUserByUsernameQuery.setString(1, username);
            ResultSet resultSet = getUserByUsernameQuery.executeQuery();

            List<User> listOfUsers = new ArrayList<>();
            while (resultSet.next()){
                listOfUsers.add(new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8), new ArrayList<>()));
            }

            if (listOfUsers.size()==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(String name, String surname, String username, String email, String password,
                        String doctorName, String doctorSurname, List<Integer> allergies) {
        try {
            ResultSet resultSet = getIdForNewUser.executeQuery();
            Integer idOfNewUser = resultSet.getInt(1);
            insertNewUserQuery.setString(1,name);
            insertNewUserQuery.setString(2,surname);
            insertNewUserQuery.setString(3,username);
            insertNewUserQuery.setString(4,email);
            insertNewUserQuery.setString(5,password);
            insertNewUserQuery.setString(6,doctorName);
            insertNewUserQuery.setString(7,doctorSurname);
            insertNewUserQuery.execute();

            for (Integer integer: allergies) {
                insertAllergyForUser.setInt(1, idOfNewUser);
                insertAllergyForUser.setInt(2, integer);
                insertAllergyForUser.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Item> getCheckoutItemsForUser(User currentUser) {
        ObservableList<Item> items = FXCollections.observableArrayList();
        try {
            getCheckoutItemsForUserQuery.setInt(1, currentUser.getId());
            ResultSet resultSet = getCheckoutItemsForUserQuery.executeQuery();

            items = getItemsFromRS(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    private ObservableList<Item> getItemsFromRS(ResultSet resultSet) {

        ObservableList<Item> items = FXCollections.observableArrayList();
        try {
             while (resultSet.next()){
                 Drug drug = ApothecaryDAO.getInstance().getDrugById(resultSet.getInt(2));
                 items.add(new Item(resultSet.getInt(1), drug, resultSet.getInt(3),
                        resultSet.getInt(4)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return items;
    }

    public User getUser(String username) {
        try {
            getUserByUsernameQuery.setString(1, username);
            ResultSet resultSet = getUserByUsernameQuery.executeQuery();

            List<User> listOfUsers = new ArrayList<>();
            while (resultSet.next()){
                listOfUsers.add(new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8), new ArrayList<>()));
            }

            if (listOfUsers.size()==1){
                return listOfUsers.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeItem(Item selectedItem) {
        try {
            deleteItemQuery.setInt(1, selectedItem.getId());
            deleteItemQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItem(Item newItem) {
        try {
            addItemQuery.setInt(1, newItem.getDrug().getId());
            addItemQuery.setInt(2, newItem.getAmount());
            addItemQuery.setInt(3, newItem.getBuyerId());
            addItemQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User currentUser) {
        try {
            updateUserQuery.setString(1, currentUser.getName());
            updateUserQuery.setString(2, currentUser.getSurname());
            updateUserQuery.setString(3, currentUser.getUsername());
            updateUserQuery.setString(4, currentUser.geteMail());
            updateUserQuery.setString(5, currentUser.getPassword());
            updateUserQuery.setString(6, currentUser.getDoctorName());
            updateUserQuery.setString(7, currentUser.getDoctorSurname());
            updateUserQuery.setInt(8, currentUser.getId());
            updateUserQuery.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
