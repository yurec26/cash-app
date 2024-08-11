package org.example.cash_app.controller;

import org.example.cash_app.model.Transaction;
import org.example.cash_app.model.TransactionId;
import org.example.cash_app.service.CashService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CashController {
    private final CashService cashService;


    public CashController(CashService cashService) {
        this.cashService = cashService;
    }


    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transfer(@Validated @RequestBody Transaction transactionReceived) {
        return cashService.transfer(transactionReceived);
    }


    @PostMapping("/confirmOperation")
    public ResponseEntity<Map<String, Object>> confirmOperation(@RequestBody TransactionId id) {
        ResponseEntity<Map<String, Object>> response = cashService.confirmOperation(id);
        System.out.println(response);
        return response;
    }
}
