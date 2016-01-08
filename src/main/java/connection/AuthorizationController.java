package connection;

import work.Permissions;
import work.Role;
import work.User;

public class AuthorizationController {

    private final ConnectionController connectionController;
    private final AuthenticationController authenticationController;

    public AuthorizationController(ConnectionController connectionController, AuthenticationController authenticationController){
        this.connectionController = connectionController;
        this.authenticationController = authenticationController;
    }

    public User getUser(String user){
        return connectionController.getUserInfo(user);
    }

    public Permissions getPermission(String login, String role, String rec){
        final User user = getUser(login);
        Role r = Role.valueOf(role);

        for(Permissions a : connectionController.getPermission(user, r)){
            if(rec.concat(".").startsWith(a.getResource()))//StupidGenius V2.0
                if(role.equals(a.getRoles().toString()))
                    return a;
        }
        return null;
    }

    public boolean isUserExist(String username){
        return getUser(username) != null;
    }

    public boolean isPassCorrect(String login, String password){
        final User user = getUser(login);
        return authenticationController.validatePassword(password, user.getPassword(),user.getSalt());
    }

    public boolean isRoleExist(String role) {
        try {
            Role.valueOf(role);
            return true;
        } catch (IllegalArgumentException exept) {
            return false;
        }
    }

    public boolean isAccessPermission(String login, String role, String rec) {
        return getPermission(login,role,rec) != null;
    }
}
