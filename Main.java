import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TestBD testBD = new TestBD();
        Scanner sc = new Scanner(System.in);
        User user = new User();

        System.out.println("������� login, password:");

        System.out.print("login: "); // ���� login
        String login = sc.nextLine();

        System.out.print("password: "); // ���� password
        String password = sc.nextLine();

        user.setData(login, password);
        System.out.println(testBD.checkUser(user));

        System.out.print("resource: "); // A B C
        String resource = sc.nextLine();

        System.out.print("permission: "); // �������� ����
        int permission=-1;
        if (sc.hasNextInt()) {
            permission = sc.nextInt();
        } else {
            System.out.println("���� �� �������");
        }

        testBD.checkPermissionsResource(resource,permission);




    }
}