package Work;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestBD { // база пользователей

    ArrayList<User> listUsers = new ArrayList<User>();
    ArrayList<Permissions> listRoles = new ArrayList<Permissions>();

    {
        // password: no1234
        listUsers.add(new User("Alexeyyy322","5bbad9d2832819b4571d6c173afff8be","0d45e2ac0d121d45e09d0bd9a6f0f555"));

        // password: yes1234
        listUsers.add(new User("DDmitry","f9247f6ccc1af05f3b6c241aea78c8d0","c927fadaf32cc04a3923af1ca77a66ab"));

        listRoles.add(new Permissions(listUsers.get(0).getLogin(),"A.B",Role.READ));
        listRoles.add(new Permissions(listUsers.get(0).getLogin(),"A.B.C",Role.WRITE));
        listRoles.add(new Permissions(listUsers.get(1).getLogin(),"A.D",Role.READ));
    }

    public TestBD() {
    }

    public int checkUser(User user) { // проверка пользователя на присутствие в бд
        if(user.getLogin()==null) return 1;
        for (int i = 0; i < listUsers.size(); i++) {
            if (user.getLogin().equals(listUsers.get(i).getLogin())) {

                // hash(hash(password)+salt)
                if (listUsers.get(i).getPassword().equals(md5Custom(md5Custom(user.getPassword()) + listUsers.get(i).getSalt())))
                    return 0;
                else return 2;

            }
        }
        return 1;
    }

    public int checkPermissionsResource(User user, String resource, String permission) { // проверка прав пользователя
        if (checkUser(user) != 0) return checkUser(user);
        try {
            Role.valueOf(permission);
        } catch (IllegalArgumentException exept){
            return 3;
        }

        for(int i = 0;i<listRoles.size();i++) {
            if (listRoles.get(i).getLoginUser().equals(user.getLogin()))
                if(resource.startsWith(listRoles.get(i).getResource()))
                    if(permission.equals(listRoles.get(i).getRoles().toString()))
                        return 0;

        }
        return 4;
    }

    public int checkDate(User user, String resource, String permission, String dateOne, String dateTwo, String vol) {
        if (checkPermissionsResource(user, resource, permission) != 0)
            return checkPermissionsResource(user, resource, permission);

        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            formatter.setLenient(false);
            calendar.setTime(formatter.parse(dateOne));

            formatter.setLenient(false);
            calendar.setTime(formatter.parse(dateTwo));

            for (int i = 0; i <vol.length(); i++) {
                if (Character.isDigit(vol.charAt(i)) == false) {
                    return 5;
                }
            }
            return 0;
        }
        catch (Exception e) {
            return 5;
        }

    }

    private static String md5Custom(String st) { // ленивое хеширование md5
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
        for (int i = 0; i < listUsers.size(); i++) {
            if (login.equals(listUsers.get(i).getLogin())) return i;
        }
        return -1;
    }
}