package work;

public enum ReturnCode {
    HELP(255),
    NORMAL(0),
    UNKNOWNUSER(1),
    UNKNOWNPASSWORD(2),
    UNKNOWNROLE(3),
    ACCESSDENIED(4),
    INCORRECTACTIVITY(5);

    public int getExitCode() {
        return value;
    }

    private final int value;

    ReturnCode(int value) {
        this.value = value;
    }
}
