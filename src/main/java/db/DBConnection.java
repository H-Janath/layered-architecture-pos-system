package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private  final  Connection connection ;
    private  static DBConnection dbConnection;
    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "janath");
    }
    public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
        if(dbConnection==null) dbConnection = new DBConnection();
        return dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }
}
