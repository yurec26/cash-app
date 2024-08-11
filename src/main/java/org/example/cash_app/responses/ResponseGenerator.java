package org.example.cash_app.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResponseGenerator {

    public static final String MSG = "message";
    public static final String ID = "id";
    public static final String ZERO = "0";
    //
    public static final String MSG_200 = "Success confirmation";
    public static final String MSG_400 = "Error input data";
    public static final String MSG_500 = "Error confirmation";

    // генератор ответов в соответствии с тз для метода confirm
    public static ResponseEntity<Map<String, Object>> generateResponse(String id, String msg, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(MSG, msg);
        errorResponse.put(ID, id);
        return ResponseEntity.status(status).body(errorResponse);
    }

    // генератор ответов в соответствии с тз для метода transfer
    // на случай некорретного ввода данных (валидация отслеживается и перехватывается автоматически)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorResponses = new HashMap<>();
        errorResponses.put(ID, ZERO);
        errorResponses.put(MSG, MSG_400);
        return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
    }
}
