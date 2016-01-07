package work;

public class User {

    private final Long id;
    private final String name;
    private final String login;
    private final String password;
    private final String salt;

    public User(Long id, String name, String login, String password, String salt) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

//    public void setData(String login, String password) {
//        this.login = login;
//        this.password = password;
//    }


    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSalt(){return this.salt;}
}
