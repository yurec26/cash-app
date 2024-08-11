package org.example.cash_app.service;

import org.example.cash_app.model.Transaction;
import org.example.cash_app.model.TransactionId;
import org.example.cash_app.repository.CashRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CashService {
    private final String RESP_KEY_PARAM = "operationId";

    private final CashRepository cashRepository;

    public CashService(CashRepository cashRepository) {
        this.cashRepository = cashRepository;
    }

    public ResponseEntity<Map<String, String>> transfer(Transaction transactionReceived) {
        String confID = cashRepository.getConfId(transactionReceived);
        Map<String, String> response = new HashMap<>();
        response.put(RESP_KEY_PARAM, confID);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> confirmOperation(TransactionId id) {
        return cashRepository.confirmOperation(id);
    }
}
