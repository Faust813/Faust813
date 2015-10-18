package Work;

public class User {

    private String login;

    private String password;
    
    private String salt;

    public User() {
        this.login = "";
        this.password = "";
        this.salt = "";
    }

    public User(String login, String password, String salt) {
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    public void setData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSalt(){return this.salt;}
}
