package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.exceptions.TeapotException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "HTTP Teapot Service", description = "Implements an endpoint that returns HTTP status 418 (I'm a teapot), showcasing API extensibility.")
public class TeapotController {
    @Operation(
            summary = "The Teapot Endpoint",
            description = "Invoking this endpoint " +
                          "causes the server to brew a teapot instead of coffee. Not so useful for " +
                          "actual brewing, but a great way to check if your client can handle teapots.",
            responses = {
                    @ApiResponse(
                            responseCode = "418",
                            description = "I'm a teapot"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error - If the teapot brewing goes terribly wrong."
                    )
            }
    )
    @GetMapping("/teapot")
    public void throwTeapotException() {
        throw new TeapotException();
    }
}