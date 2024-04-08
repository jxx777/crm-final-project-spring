package itschool.crmfinalproject.model;

public record ResponseModel(
        Integer statusCode,
        String statusName,
        String message,
        Object data
) { }