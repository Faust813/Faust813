import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        User user = new User();

        System.out.println("¬ведите login, password:");
        System.out.print("login: ");
        if (sc.hasNextLine()) {
            user.setLogin(sc.nextLine());

            System.out.print("password: ");
            if (sc.hasNextLine()) {
                user.setPassword(sc.nextLine());
            } else {
                System.out.println("-");
            }

        } else {
            System.out.println("-");
        }

    }
}