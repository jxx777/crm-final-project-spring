package itschool.crmfinalproject.exceptions;

public class UnsupportedEntityTypeException extends RuntimeException {
    public UnsupportedEntityTypeException(String entityType) {
        super("Information about '" + entityType + "' is not supported for export.");
    }
}