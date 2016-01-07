package work;

import org.apache.commons.cli.*;
import java.io.PrintWriter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import connection.*;

public class Main {

    private final ConnectionController connectionController = new ConnectionController();
    private final AuthenticationController authenticationController = new AuthenticationController();
    private final AuthorizationController authorizationController = new AuthorizationController(connectionController, authenticationController);
    private final AccountingController accountingController = new AccountingController(connectionController);
    private final Options options;
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        ReturnCode returnCode = new Main().connectBD(args);
        System.exit(returnCode.getExitCode());
    }

    public Main(){
        options = new Options()
                .addOption("h", false, "print this help message")
                .addOption("login", true, "login")
                .addOption("pass", true, "password")
                .addOption("res", true, "resource")
                .addOption("role", true, "permission")
                .addOption("ds", true, "date start")
                .addOption("de", true, "date end")
                .addOption("vol", true, "value");
    }

    public ReturnCode connectBD(String[] args){
        try{
            connectionController.connect();
            return work(args);
        }finally {
            connectionController.disconnect();
        }
    }

    private ReturnCode work(String[] args) {
        logger.info("Ввод параметров {}",(Object) args);
        CommandLineParser parser = new DefaultParser();
        ReturnCode result;
        try{
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("h")){
                logger.info("Вывод справки");
                printHelp(options);
                result = ReturnCode.NORMAL;
            } else {
                if(cmd.hasOption("login") && cmd.hasOption("pass")){
                    String login = cmd.getOptionValue("login");
                    String password = cmd.getOptionValue("pass");
                    logger.info("Получение логина {} и пароля {}",login,password);
                    result = authentication(login, password);

                    if(result == ReturnCode.NORMAL && cmd.hasOption("res") && cmd.hasOption("role")){
                        String resource = cmd.getOptionValue("res");
                        String permission = cmd.getOptionValue("role");
                        logger.info("Получение ресурса {} и роли {}", resource, permission);
                        result = permissionAccess(login, permission, resource);

                        if(result == ReturnCode.NORMAL && cmd.hasOption("ds") && cmd.hasOption("de") && cmd.hasOption("vol")){
                            String date1 = cmd.getOptionValue("ds");
                            String date2 = cmd.getOptionValue("de");
                            String vol = cmd.getOptionValue("vol");
                            logger.info("Получение дат {} {} и объёма {}",date1,date2,vol);
                            result = account(login,permission,resource,date1,date2,vol);
                        }
                    }
                } else {
                    logger.info("Отсутствуют ключи для логина и пароля");
                    printHelp(options);
                    result = ReturnCode.HELP;
                }
            }
        }catch (org.apache.commons.cli.ParseException e){
            logger.error("ParseException:",e);
            printHelp(options);
            result = ReturnCode.HELP;
        }
        logger.info("Результат: {}",result);
        return result;
    }

    private ReturnCode authentication(String login, String password) {
        if(!authorizationController.isUserExist(login)) {
            logger.warn("Пользователь с ником {} не найден",login);
            return ReturnCode.UNKNOWNUSER;
        } else if(!authorizationController.isPassCorrect(login, password)) {
            logger.warn("Неверный пароль {}",password);
            return ReturnCode.UNKNOWNPASSWORD;
        } else {
            logger.info("Осуществлён вход пользователя {} с паролем {}",login,password);
            return ReturnCode.NORMAL;
        }
    }

    private ReturnCode permissionAccess(String login, String role, String rec) {
        if(!authorizationController.isRoleExist(role)) {
            logger.warn("Роль {} не найдена", role);
            return ReturnCode.UNKNOWNROLE;
        } else if(!authorizationController.isAccessPermission(login, role, rec)) {
            logger.warn("Пользователь {} не имеет доступа с ролью {} к ресурсу {}",login,role,rec);
            return ReturnCode.ACCESSDENIED;
        } else {
            logger.info("Пользователь {} авторизирован на ресурсе {} с ролью {}",login,rec,role);
            return ReturnCode.NORMAL;
        }
    }

    private ReturnCode account(String login,String role, String rec, String date_start, String date_end, String vol){
        try{
        accountingController.addActivity(authorizationController.getPermission(login, role, rec),
                date_start, date_end, vol);
            logger.info("Успешный вход выполнен пользователем {}",login);
            return ReturnCode.NORMAL;
        } catch (java.text.ParseException e) {
            logger.warn("Неверный формат для даты {} или {}", date_start, date_end);
            return ReturnCode.INCORRECTACTIVITY;
        } catch (NumberFormatException e) {
            logger.warn("Возможно, что это не число: {}",vol);
            return ReturnCode.INCORRECTACTIVITY;
        }
    }

    private static void printHelp(Options options) {

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