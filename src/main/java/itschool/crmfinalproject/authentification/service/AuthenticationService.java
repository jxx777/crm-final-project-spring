package itschool.crmfinalproject.authentification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.authentification.model.RequestAuthenticationDTO;
import itschool.crmfinalproject.authentification.model.RequestRegistrationDTO;
import org.springframework.http.ResponseEntity;

/**
 * Interface for authentication-related operations.
 */
public interface AuthenticationService {

    /**
     * Authenticate a user based on the provided login credentials.
     *
     * @param requestAuthenticationDTO the login credentials
     * @return a response entity indicating the outcome of the authentication process
     * @throws JsonProcessingException if there is a processing error
     */
    ResponseEntity<String> accessAccount(RequestAuthenticationDTO requestAuthenticationDTO) throws JsonProcessingException;

    /**
     * Register a new user with the given registration fieldDetails.
     *
     * @param requestRegistrationDTO the user registration fieldDetails
     * @return a response entity indicating the outcome of the registration process
     * @throws JsonProcessingException if there is a processing error
     */
    ResponseEntity<String> createAccount(RequestRegistrationDTO requestRegistrationDTO) throws JsonProcessingException;
}