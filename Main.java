import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TestBD testBD = new TestBD();
        Scanner sc = new Scanner(System.in);
        User user = new User();

        System.out.println("¬ведите login, password:");

        System.out.print("login: ");
        user.setLogin(sc.nextLine());

        System.out.print("password: ");
        user.setPassword(sc.nextLine());

        System.out.println(testBD.checkUser(user));
    }
}