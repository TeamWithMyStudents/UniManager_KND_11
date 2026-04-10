package ua.knd11.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class SQLActions {

    private static final String DATABASE_URL = System.getenv("DATABASE_URL");

    /**
     * Establishes and returns a connection to the database using the configured database URL.
     *
     * @return a {@link Connection} instance representing the active database connection
     * @throws SQLException if a database access error occurs or the URL is invalid
     */

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void executeSQLQuery(String sql) {
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Database operation failed: " + e.getMessage());
        }
    }
}
