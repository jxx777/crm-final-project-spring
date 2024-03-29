package itschool.crmfinalproject.exceptions;

public class UnsupportedFormatException extends RuntimeException {
    public UnsupportedFormatException(String format) {
        super("Export format '." + format + "' is not supported.");
    }
}