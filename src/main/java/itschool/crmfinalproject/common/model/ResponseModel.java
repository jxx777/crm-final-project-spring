package itschool.crmfinalproject.common.model;

public record ResponseModel(
        Integer statusCode,
        String statusName,
        String message,
        Object data
) { }