import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestBD { // база пользователей

    private User[] listUsers = new User[10];
    private String[] salt = new String[10];
    private Role[] roles = new Role[10];
    private static String resource = "A.B.C";


    {

        listUsers[0] = new User("Alexeyyy322", "5bbad9d2832819b4571d6c173afff8be");
        salt[0] = "0d45e2ac0d121d45e09d0bd9a6f0f555"; // password: no1234
        roles[0] = new Role(4, "B");

        listUsers[1] = new User("DDmitry", "f9247f6ccc1af05f3b6c241aea78c8d0");
        salt[1] = "c927fadaf32cc04a3923af1ca77a66ab"; // password: yes1234
        roles[1] = new Role(2, "B");
    }

    public TestBD() {
    }

    public int checkUser(User user) { // проверка пользователя на присутствие в бд
        for (int i = 0; listUsers[i] != null; i++) {
            if (user.getLogin().equals(listUsers[i].getLogin())) {

                // hash(hash(password)+salt)
                if (listUsers[i].getPassword().equals(md5Custom(md5Custom(user.getPassword()) + salt[i])))
                    return 0;
                else return 2;

            }
        }
        return 1;
    }

    public int checkPermissionsResource(User user, String resource, int permission) { // проверка прав пользователя
        if (checkUser(user) != 0) return checkUser(user);
        if ((permission < 0) && (permission > 7)) return 3;
        if (permission != roles[foundLogin(user.getLogin())].getPer()) return 4;

        int a = TestBD.resource.indexOf(roles[foundLogin(user.getLogin())].getRes());
        int b = TestBD.resource.indexOf(resource);

        if(b<a)return 4;
        return 0;
    }

    public static String md5Custom(String st) { // ленивое хеширование md5
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

    private int foundLogin(String login) {
        for (int i = 0; listUsers[i] != null; i++) {
            if (login.equals(listUsers[i].getLogin())) return i;
        }
        return -1;
    }
}

class Role {

    private int per;
    private String res;

    public Role() {
        this.per = 0;
        this.res = "";
    }

    public Role(int per, String res) {
        this.per = per;
        this.res = res;
    }

    public void setRole(int per, String res) {
        this.per = per;
        this.res = res;
    }

    public int getPer() {
        return this.per;
    }

    public String getRes() {
        return this.res;
    }

}
