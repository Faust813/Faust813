package db.connection;
import java.sql.*;

public class ConnectionBD {

    private final String url = "jdbs:h2:./aaa";
    private final String user = "super";
    private final String password = null;
    private Connection connection;

    public void connect(){
        try{
            connection = getConnect();
        }catch (SQLException e){
            System.out.println(e);
        }
    }
    public void disconnect(){
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e);
            }
        }
    }

    private Connection getConnect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
