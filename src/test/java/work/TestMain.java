package work;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMain {
    Main main;

    @Before
    public void init() {
        main = new Main();
    }

    @Test
    public void empty() {
        assertEquals(main.connectBD(new String[]
                {}),
                ReturnCode.HELP);
    }

    @Test
    public void help() {
        assertEquals(main.connectBD(new String[]
                {"-h"}),
                ReturnCode.NORMAL);
    }

    @Test
    public void unknownLogin() {
        assertEquals(main.connectBD(new String[]
                {"-login", "XXX", "-pass", "XXX"}),
                ReturnCode.UNKNOWNUSER);
    }

    @Test
    public void invalidPassword() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "XXX"}),
                ReturnCode.UNKNOWNPASSWORD);
    }

    @Test
    public void successfulAuthentication() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ"}),
                ReturnCode.NORMAL);
    }

    @Test
    public void athorize() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ","-role", "READ", "-res", "a"}),
                ReturnCode.NORMAL);
    }

    @Test
    public void authorizeNested() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ","-role", "READ", "-res", "a.b"}),
                ReturnCode.NORMAL);
    }

    @Test
    public void unknownRole() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ","-role", "XXX", "-res", "a.b"}),
                ReturnCode.UNKNOWNROLE);
    }

    @Test
    public void incorrectResource() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ","-role", "READ", "-res", "XXX"}),
                ReturnCode.ACCESSDENIED);
    }

    @Test
    public void accessDenied() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ","-role", "WRITE", "-res", "a"}),
                ReturnCode.ACCESSDENIED);
    }

    @Test
    public void accessDeniedNested() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ","-role", "WRITE", "-res", "a.bc"}),
                ReturnCode.ACCESSDENIED);
    }

    @Test
    public void accounting() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ",
                 "-role", "READ", "-res", "a",
                 "-ds", "2015-05-01", "-de", "2015-05-02", "-vol", "100"}),
                ReturnCode.NORMAL);
    }

    @Test
    public void invalidDate() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ",
                 "-role", "READ", "-res", "a",
                 "-ds", "XXX", "-de", "XXX", "-vol", "XXX"}),
                ReturnCode.INCORRECTACTIVITY);
    }

    @Test
    public void invalidVolume() {
        assertEquals(main.connectBD(new String[]
                {"-login", "jdoe", "-pass", "sup3rpaZZ",
                 "-role", "READ", "-res", "a",
                 "-ds", "2015-05-01", "-de", "2015-05-02", "-vol", "XXX"}),
                ReturnCode.INCORRECTACTIVITY);
    }
}