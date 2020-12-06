package HW2;

import HW2.data.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitialState {
    public static void createInitialState() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("CREATE TABLE CreditPoints (Faculty TEXT NOT NULL,\n" +
                    "Points INTEGER NOT NULL,\n" +
                    "PRIMARY KEY(Faculty),\n" +
                    "CHECK(Points>0))");
            pstmt.execute();

            pstmt = connection.prepareStatement("INSERT INTO CreditPoints(Faculty,Points) VALUES (?, ?), (?, ?), (?, ?);");
            pstmt.setString(1, "CS");
            pstmt.setInt(2, 120);
            pstmt.setString(3, "EE");
            pstmt.setInt(4, 160);
            pstmt.setString(5, "MATH");
            pstmt.setInt(6, 115);
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearInitialState() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DELETE FROM CreditPoints;");
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void dropInitialState() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS CreditPoints");
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
