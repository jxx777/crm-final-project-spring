//package itschool.crmfinalproject.utility;
//
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import itschool.crmfinalproject.model.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//
//public class GenerateResponse {
//    public static ResponseEntity<String> success(String message, Object data) throws JsonProcessingException {
//        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 200)), HttpStatus.OK);
//    }
//
//    public static ResponseEntity<String> created(String message, Object data) throws JsonProcessingException {
//        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 201)), HttpStatus.CREATED);
//    }
//
//    public static ResponseEntity<String> error(String message, Object data) throws JsonProcessingException {
//        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 500)), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    public static ResponseEntity<String> notFound(String message, Object data) throws JsonProcessingException {
//        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 404)), HttpStatus.NOT_FOUND);
//    }
//
//    public static ResponseEntity<String> unauthorized(String message, Object data) throws JsonProcessingException {
//        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 401)), HttpStatus.UNAUTHORIZED);
//    }
//
//    public static ResponseEntity<String> badRequest(String message, Object data) throws JsonProcessingException {
//        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new Response(message, data, 400)), HttpStatus.BAD_REQUEST);
//    }
//}

package itschool.crmfinalproject.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itschool.crmfinalproject.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GenerateResponse {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private static ResponseEntity<String> createResponse(String message, Object data, HttpStatus status) throws JsonProcessingException {
        Response response = new Response(
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

    // Methods for other HTTP status responses (notFound, unauthorized, badRequest, etc.) can be added here...
}

