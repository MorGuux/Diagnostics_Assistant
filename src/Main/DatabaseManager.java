package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseManager {

    private static Connection _connection;

    /**
     * Open the connection to the database
     * @throws SQLException
     */
    public static void openConnection() throws SQLException {
        try
        {
            Class.forName("org.sqlite.JDBC");
            _connection = DriverManager.getConnection("jdbc:sqlite:diagnostic_assistant.db");
        } catch (Exception ex)
        {
            throw new SQLException();
        }
    }

    public static void closeConnection() throws SQLException
    {
        _connection.close();
    }

    public static ResultSet get(String query) throws SQLException {
        Statement stmt = _connection.createStatement();
        return stmt.executeQuery(query);
    }

    public static void insert(String query) throws SQLException {
        Statement stmt = _connection.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        _connection.commit();
    }

    public static void update(String query) throws SQLException {
        Statement stmt = _connection.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        _connection.commit();
    }

    public static void delete(String query) throws SQLException {
        Statement stmt = _connection.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        _connection.commit();
    }

}
