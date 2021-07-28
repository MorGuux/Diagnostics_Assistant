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

    public static ArrayList<DatabaseClasses.Guide> getGuides() throws SQLException {
        ResultSet dbGuides = get("SELECT * FROM Guides");
        if (dbGuides != null) {
            ArrayList<DatabaseClasses.Guide> guides = new ArrayList<>();
            while (dbGuides.next()) {
                DatabaseClasses.Guide guide = new DatabaseClasses.Guide();
                guide.ID = dbGuides.getInt("ID");
                guide.Title = dbGuides.getString("Title");
                guide.Description = dbGuides.getString("Description");
                guide.Path = dbGuides.getString("Path");
                guide.Type = dbGuides.getInt("Type");
                guides.add(guide);
            }
            return guides;
        }
        return null;
    }

}
