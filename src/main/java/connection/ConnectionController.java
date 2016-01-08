package connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import work.Activity;
import work.Permissions;
import work.Role;
import work.User;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionController {

    private static final Logger logger = LogManager.getLogger(ConnectionController.class);
    private final String url = "jdbc:h2:./TRPO_AD";
    private final String user = "BEHAPPY";
    private final String password = null;
    private Connection connection;

    public void connect() {
        logger.info("Подключение Базы Данных ...");
        try{
            migrate();
            connection = getConnect();
        }catch (SQLException e){
            logger.error("Невозможно подключить Базу Данных", e);
            System.out.println(e);
        }
        logger.info("Подключение выполнено");
    }

    public void disconnect() {
        logger.info("Отключение Базы Данных ...");
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e){
                logger.error("Невозможно отключить Базу Данных", e);
                System.out.println(e);
            }
        }
        logger.info("Отключение выполнено");
    }

    public void migrate(){
        logger.info("Осуществление миграции Базы Данных ...");
        Flyway flyway = new Flyway();
        flyway.setDataSource(url,user,password);
        try{
            flyway.migrate();
        } catch (FlywayException e){
            logger.error("Невозможно осуществить миграцию Базы Данных", e);
            System.out.println(e);
        }
        logger.info("Миграция осуществлена");
    }

    public User getUserInfo(String login){
        PreparedStatement query = null;
        try{
            query = connection.prepareStatement("SELECT * FROM USER_ACCOUNT WHERE LOGIN = ?");
            query.setString(1, login);
            ResultSet execute = query.executeQuery();
            if (execute.next()) {
                return new User(
                        execute.getLong("id"),
                        execute.getString("name"),
                        execute.getString("login"),
                        execute.getString("password"),
                        execute.getString("salt"));
            }
        } catch (SQLException e){
            logger.error("Невозможно выполнить запрос", e);
            System.out.println(e);
        } finally {
            if(query != null){
               try{query.close();} catch (SQLException e){}
            }
        }
        return null;
    }

    public ArrayList<Permissions> getPermission(User user, Role role ) {
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement("SELECT * FROM PERMISSIONS WHERE USER_ID = ? AND ROLE = ?");
            query.setLong(1, user.getId());
            query.setString(2,role.toString());
            ResultSet execute = query.executeQuery();
            ArrayList<Permissions> listRoles = new ArrayList<>();
            while(execute.next()) {
                listRoles.add(new Permissions(
                        execute.getLong("id"),
                        user,
                        Role.valueOf(execute.getString("role")),
                        execute.getString("site")
                ));
            }
            return listRoles;

        } catch (SQLException e) {
            logger.error("Невозможно выполнить запрос", e);
            System.out.println(e);
        } finally {
            if(query != null){
                try{query.close();} catch (SQLException e){}
            }
        }
        return null;
    }

    public void addActivity(Activity activity) {
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement("INSERT INTO ACTIVITY (AUTHORITY_ID,LOGIN_DATE,LOGOUT_DATE,VOLUME) VALUES (?,?,?,?)");
            query.setLong(1, activity.getPermission().getId());
            query.setDate(2, new java.sql.Date(activity.getLoginDate().getTime()));
            query.setDate(3, new java.sql.Date(activity.getLogoutDate().getTime()));
            query.setLong(4, activity.getVolume());
            query.execute();
        } catch (SQLException e) {
            logger.error("Невозможно выполнить запрос", e);
            System.out.println(e);
        } finally {
            if(query != null){
                try{query.close();} catch (SQLException e){}
            }
        }
    }

    private Connection getConnect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
