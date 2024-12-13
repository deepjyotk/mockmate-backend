package com.mockmate.auth_service.exception.handler;

import com.mockmate.auth_service.dto.exception.ExceptionResponseDto;
import com.mockmate.auth_service.exception.custom.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ExceptionResponseDto ExceptionResponseDto = new ExceptionResponseDto(
                "1",
                "Not Found" ,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                null,
                ex.getMessage(),
                ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(ExceptionResponseDto, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(SlotAlreadyBookedException.class)
    public ResponseEntity<ExceptionResponseDto> handleSlotAlreadyBookedException(SlotAlreadyBookedException ex) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                "6",
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                null
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class, EmailAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponseDto> handleConflictExceptions(Exception ex) {
        ExceptionResponseDto ExceptionResponseDto = new ExceptionResponseDto(
                "2" ,
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                null
        );
        return new ResponseEntity<>(ExceptionResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage())
        );

        ExceptionResponseDto ExceptionResponseDto = new ExceptionResponseDto(
                "3",
                "Validation failed for one or more fields",
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                validationErrors
        );
        return new ResponseEntity<>(ExceptionResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                validationErrors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        ExceptionResponseDto ExceptionResponseDto = new ExceptionResponseDto(
                "4",
                "Constraint violation occurred",
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                validationErrors
        );
        return new ResponseEntity<>(ExceptionResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleGenericException(Exception ex) {

        ExceptionResponseDto ExceptionResponseDto = new ExceptionResponseDto(
                "5",
                "Something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                null,
                ex.getMessage(),
                ex.fillInStackTrace().toString()
        );
        return new ResponseEntity<>(ExceptionResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WaitingForPeerException.class)
    public ResponseEntity<ExceptionResponseDto> handleWaitingForPeerException(WaitingForPeerException ex) {
        Map<String, String> additionalDetails = new HashMap<>();
        additionalDetails.put("exceptionType", ex.getClass().getSimpleName());
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                "7",
                "User is currently waiting for a peer to join",
                HttpStatus.ACCEPTED.value(),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                additionalDetails,
                ex.getMessage(),
                ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(NoPeerMatchedYetException.class)
    public ResponseEntity<ExceptionResponseDto> handleNoPeerMatchedYetException(NoPeerMatchedYetException ex) {
        Map<String, String> additionalDetails = new HashMap<>();
        additionalDetails.put("exceptionType", ex.getClass().getSimpleName());
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                "8",
                "No peer has been matched for the user yet",
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                additionalDetails,
                ex.getMessage(),
                ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PeerWaitingThresholdReachedException.class)
    public ResponseEntity<ExceptionResponseDto> handlePeerWaitingThresholdReachedException(PeerWaitingThresholdReachedException ex) {
        Map<String, String> additionalDetails = new HashMap<>();
        additionalDetails.put("exceptionType", ex.getClass().getSimpleName());

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                "9",
                "The waiting threshold for a peer has been exceeded",
                HttpStatus.REQUEST_TIMEOUT.value(),
                HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(),
                additionalDetails,
                ex.getMessage(),
                ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(CouldNotMatchInterviewFailureException.class)
    public ResponseEntity<ExceptionResponseDto> handlePeerWaitingThresholdReachedException(CouldNotMatchInterviewFailureException ex) {
        Map<String, String> additionalDetails = new HashMap<>();
        additionalDetails.put("exceptionType", ex.getClass().getSimpleName());

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                "9",
                "The waiting threshold for a peer has been exceeded",
                HttpStatus.REQUEST_TIMEOUT.value(),
                HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(),
                additionalDetails,
                ex.getMessage(),
                ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.REQUEST_TIMEOUT);
    }


    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<ExceptionResponseDto> handleNotAllowedException(NotAllowedException ex) {
        Map<String, String> additionalDetails = new HashMap<>();
        additionalDetails.put("exceptionType", ex.getClass().getSimpleName());

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                "10", // Unique error code
                "Operation not allowed",
                HttpStatus.FORBIDDEN.value(), // Use appropriate status code, e.g., 403 Forbidden
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                additionalDetails, // No additional details for now
                ex.getMessage(),
                ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.FORBIDDEN);

    }

}
