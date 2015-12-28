package work;

import db.connection.ConnectionBD;
import org.apache.commons.cli.*;

import java.io.PrintWriter;

public class Main {

    private final ConnectionBD connectBD = new ConnectionBD();
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final AuthorizationService authorizationService = new AuthorizationService(connectBD, authenticationService);
    private final AccountingService accountingService = new AccountingService(connectBD);

    public static void main(String[] args) {
        ReturnCode returnCode = work(args);
        System.exit(returnCode.getExitCode());
    }

    public static ReturnCode work(String[] args) {

        TestBD testBD = new TestBD();
        User user = new User();
        String login = "";
        String password = "";
        String resource = "";
        String permission = "";
        String date1 = "";
        String date2 = "";
        String vol = "";
        int arguments = 0;


        Options options = new Options()
                .addOption("h", false, "print this help message")
                .addOption("login", true, "login")
                .addOption("pass", true, "password")
                .addOption("res", true, "resource")
                .addOption("role", true, "permission")
                .addOption("ds", true, "date start")
                .addOption("de", true, "date end")
                .addOption("vol", true, "value");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("login")) {
                login = cmd.getOptionValue("login");
                arguments = 1;
            }
            if (cmd.hasOption("pass")) {
                password = cmd.getOptionValue("pass");
                arguments = 1;
            }
            if (cmd.hasOption("res")) {
                resource = cmd.getOptionValue("res");
                arguments = 2;
            }
            if (cmd.hasOption("role")) {
                permission = cmd.getOptionValue("role");
                arguments = 2;
            }
            if (cmd.hasOption("ds")) {
                date1 = cmd.getOptionValue("ds");
                arguments = 3;
            }
            if (cmd.hasOption("de")) {
                date2 = cmd.getOptionValue("de");
                arguments = 3;
            }
            if (cmd.hasOption("vol")) {
                vol = cmd.getOptionValue("vol");
                arguments = 3;
            }

            user.setData(login, password);
            if (arguments == 3)
                return testBD.checkDate(user, resource, permission, date1, date2, vol);
            else if (arguments == 2)
                return testBD.checkPermissionsResource(user, resource, permission);
            else if (arguments == 1)
                return testBD.checkUser(user);
            else if (args.length == 0 || cmd.hasOption("h")) {
                throw new org.apache.commons.cli.ParseException("help");
            }

        } catch (org.apache.commons.cli.ParseException e) {
            printHelp(options);
        }

        return ReturnCode.HELP;
    }

    static void printHelp(Options options) {

        final HelpFormatter formatter = new HelpFormatter();
        final PrintWriter writer = new PrintWriter(System.out);
        formatter.printHelp(writer,
                80,
                " ",
                " ----- [HELP] ----- ",
                options, 3, 5,
                " ----- [END] ----- ",
                true);
        writer.flush();
    }
}