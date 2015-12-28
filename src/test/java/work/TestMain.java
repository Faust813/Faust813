package work;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMain {
    @Test
    public void C11() {
        ReturnCode returnCode = Main.work(new String[]{});
        assertEquals(returnCode, ReturnCode.HELP);
    }

    @Test
    public void C22() {
        ReturnCode returnCode = Main.work(new String[]{"-h"});
        assertEquals(returnCode, ReturnCode.HELP);
    }
}