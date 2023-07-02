package pro.sky.myfirsttgbot.exception;

public class DateTimeParseException extends Exception {
private static final int ERROR_CODE = 32023;

    public DateTimeParseException(String message) {
        super(message);
    }
}
