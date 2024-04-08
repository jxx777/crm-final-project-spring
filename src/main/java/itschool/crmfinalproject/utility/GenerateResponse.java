package itschool.crmfinalproject.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itschool.crmfinalproject.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GenerateResponse {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private static ResponseEntity<String> createResponse(String message, Object data, HttpStatus status) throws JsonProcessingException {
        ResponseModel response = new ResponseModel(
                status.value(),
                status.name(),
                message,
                data
        );
        String responseBody = OBJECT_MAPPER.writeValueAsString(response);
        return new ResponseEntity<>(responseBody, status);
    }

    public static ResponseEntity<String> success(String message, Object data) throws JsonProcessingException {
        return createResponse(message, data, HttpStatus.OK);
    }

    public static ResponseEntity<String> created(String message, Object data) throws JsonProcessingException {
        return createResponse(message, data, HttpStatus.CREATED);
    }

    public static ResponseEntity<String> notFound(String message, Object data) throws JsonProcessingException {
        return createResponse(message, data, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<String> unauthorized(String message, Object data) throws JsonProcessingException {
        return createResponse(message, data, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<String> badRequest(String message, Object data) throws JsonProcessingException {
        return createResponse(message, data, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<String> teapot(String message, Object data) throws JsonProcessingException {
        return createResponse(message, data, HttpStatus.I_AM_A_TEAPOT);
    }
}

