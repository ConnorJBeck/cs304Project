import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

    private static Connection connection;
    private static ConnectionManager instance;

    private ConnectionManager() throws SQLException {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1522:ug", "ora_s8h0b", "a57723158");
    }

    public static void initConnection() throws SQLException {
        if(instance == null) {
            instance = new ConnectionManager();
        }
    }

    public static Statement getStatement() throws SQLException {
        if (connection == null) {
            initConnection();
        }

        return connection.createStatement();
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}