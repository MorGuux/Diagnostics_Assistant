package Main;

import java.sql.*;
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

    public static void insert(String tableName, String[]... columns) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " ('" + columns[0][0]);
        for (int i = 1; i < columns.length; i++) {
            query.append("', '" + columns[i][0]);
        }
        query.append("') VALUES (?");
        for (int i = 1; i < columns.length; i++) {
            query.append(", ?");
        }
        query.append(")");

        System.out.println(query);

        PreparedStatement stmt = _connection.prepareStatement(query.toString());

        int index = 1;
        for (String[] column : columns) {
            stmt.setString(index, column[1]);
            index++;
        }

        System.out.println(stmt);

        stmt.executeUpdate();
        stmt.close();
    }

    public static void update(String query) throws SQLException {
        Statement stmt = _connection.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }

    public static void delete(String query) throws SQLException {
        Statement stmt = _connection.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }

}
