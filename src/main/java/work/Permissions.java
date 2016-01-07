package work;

public class Permissions {

    private final Long id;
    private final User user;
    private final Role roles;
    private final String resource;

    public Permissions(Long id, User user, Role roles, String resource){
        this.id = id;
        this.user = user;
        this.resource = resource;
        this.roles = roles;
    }

    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return user;
    }

    public String getResource() {
        return resource;
    }

    public Role getRoles() {
        return roles;
    }
}
