package ba.unsa.etf.rpr.DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ApothecaryDAO {
    private static ApothecaryDAO instance = null;
    private Connection connection;
    private PreparedStatement preparedStatement;

    private ApothecaryDAO() throws SQLException {
        String url = "jdbc:sqlite:eHealthDatabase.db";
        connection = DriverManager.getConnection(url);

        try{
            preparedStatement = connection.prepareStatement("Select * from drug");
        }catch (SQLException e){
            createDatabase();
            preparedStatement = connection.prepareStatement("Select * from drug");
        }
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
}