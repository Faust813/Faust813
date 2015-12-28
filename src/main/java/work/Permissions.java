package work;

public class Permissions {

    private String loginUser;

    private String resource;

    private Role roles;

    public Permissions(String loginUser, String resource, Role roles){
        this.loginUser =loginUser;
        this.resource = resource;
        this.roles = roles;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public String getResource() {
        return resource;
    }

    public Role getRoles() {
        return roles;
    }
}
