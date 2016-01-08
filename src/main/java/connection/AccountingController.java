package connection;

import work.Activity;
import work.Permissions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AccountingController {
    private static final Calendar calendar = new GregorianCalendar();
    private static final DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    private final ConnectionController connectionController;

    public AccountingController(ConnectionController connectionController){
        this.connectionController = connectionController;
    }

    public void addActivity(Permissions permission, String ds, String de, String vol) throws ParseException, NumberFormatException {
        formatter.setLenient(false);
        calendar.setTime(formatter.parse(ds));
        formatter.setLenient(false);
        calendar.setTime(formatter.parse(de));
        connectionController.addActivity(new Activity(permission,formatter.parse(ds),formatter.parse(de),Long.valueOf(vol)));
    }
}
