package Work;


        import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TestBD testBD = new TestBD();
        Scanner sc = new Scanner(System.in);
        User user = new User();
        int temp;

        System.out.println("¬ведите login, password:");

        System.out.print("login: "); // ввод login
        String login = sc.nextLine();

        System.out.print("password: "); // ввод password
        String password = sc.nextLine();

        user.setData(login, password);
        temp = testBD.checkUser(user);
        System.out.println(temp);

        if(temp == 0) {

            System.out.print("resource: "); // A B C
            String resource = sc.nextLine();

            System.out.print("permission: "); // указание роли
            String permission = sc.nextLine();

            temp = testBD.checkPermissionsResource(user, resource, permission);
            System.out.println(temp);

            if(temp == 0) {

                System.out.print("Start Date: "); // Дата начала
                String date1 = sc.nextLine();

                System.out.print("End Date: "); // Дата окончания
                String date2 = sc.nextLine();

                System.out.print("Volume: "); // Дата окончания
                String vol = sc.nextLine();

                temp = testBD.CheckDate(user, resource, permission,date1,date2,vol);
                System.out.println(temp);

            }
        }
    }


}