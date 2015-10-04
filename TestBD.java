public class TestBD { // база пользователей

    private static User listUsers[];

    {
        listUsers[0] = new User("Alexeyyy322", "1234");
        listUsers[1] = new User("DDmitry", "4321");
    }

    public int checkUser(User user) {
        for (int i=0;listUsers[i]!=null;i++) {
            if(user.getLogin().equals(listUsers[i].getLogin())){
                if(user.getPassword().equals(listUsers[i].getPassword()))
                    return 0;
                else return 2;
            }
        }
        return 1;
    }
}
