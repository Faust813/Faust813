package work;

public enum ReturnCode {
    HELP(255),
    NORMAL(0),
    UNKNOWNUSER(1),
    UNKNOWNPASSWORD(2),
    UNKNOWNROLE(3),
    ACCESSDENIED(4),
    INCORRECTACTIVITY(5);

	private final int value;

    public int getExitCode() {
        return value;
    }

    ReturnCode(int value) {
        this.value = value;
    }
}
