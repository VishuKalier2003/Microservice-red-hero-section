package site.webred.global;

import java.nio.file.AccessDeniedException;

import javax.naming.ServiceUnavailableException;
import javax.security.sasl.AuthenticationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.MethodNotAllowedException;

// Imp- Use @ControllerAdvice and @Controller when controllers provide output in HTML views and not JSON format

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)      // .class defines the literal name passed as a parameter and not an instance of the class
    public ResponseEntity<Object> handleAllExceptions(Exception ex) throws MethodNotAllowedException {
        try {
            throw ex; // Simulate exception occurrence
        }
        catch (IllegalArgumentException e) {
            // Handle 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Incorrect JSON Body or Missing Query Parameters (Error) : " + e.getMessage());
        }
        catch (AuthenticationException e) {
            // Handle 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized user (Error) : " + e.getMessage());
        }
        catch (AccessDeniedException e) {
            // Handle 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User Authorized yet forbidden (Error) : " + e.getMessage());
        }
        catch (ResourceNotFoundException e) {
            // Handle 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Method (Http) Not Found (Error) : " + e.getMessage());
        }
        catch (HttpRequestMethodNotSupportedException e) {
            // Handle 405 Method Not Allowed
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("Method (REST api) Not Defined or Allowed (Error) : " + e.getMessage());
        }
        catch (DataIntegrityViolationException e) {
            // Handle 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflict in Data State Value (Error) : " + e.getMessage());
        }
        catch (HttpServerErrorException.BadGateway e) {
            // Handle 502 Bad Gateway
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Service (upstream) sent an invalid Response (Error) : " + e.getMessage());
        }
        catch (ServiceUnavailableException e) {
            // Handle 503 Service Unavailable
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Service (Backend) temporarily Unavailable (Error) : " + e.getMessage());
        }
        catch (HttpServerErrorException.GatewayTimeout e) {
            // Handle 504 Gateway Timeout
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                    .body("Response takes too long to process (Error) : " + e.getMessage());
        }
        catch (Exception e) {
            // Handle 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("General Server Problem (Error) : " + e.getMessage());
        }
        finally {
            System.out.println("Execution completed, logging if needed....");
        }
    }
}
