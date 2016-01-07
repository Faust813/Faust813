package work;

import java.util.Date;

public class Activity {
    private final Long id;
    private final Permissions permission;
    private final Date loginDate;
    private final Date logoutDate;
    private final Long volume;

    public Activity(Long id, Permissions permission, Date loginDate, Date logoutDate, Long volume) {
        this.id = id;
        this.permission = permission;
        this.loginDate = loginDate;
        this.logoutDate = logoutDate;
        this.volume = volume;
    }

    public Activity(Permissions permission, Date loginDate, Date logoutDate, Long volume) {
        this(null,permission,loginDate,logoutDate,volume);
    }

    public Long getId() {
        return this.id;
    }

    public Permissions getPermission() {
        return this.permission;
    }

    public Date getLoginDate() {
        return this.loginDate;
    }

    public Date getLogoutDate() {
        return this.logoutDate;
    }

    public Long getVolume() {
        return this.volume;
    }
}
