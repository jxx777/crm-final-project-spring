package itschool.crmfinalproject.model;

public record Response(Integer statusCode, String statusName, String message, Object data) { }