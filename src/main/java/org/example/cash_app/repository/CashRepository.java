package org.example.cash_app.repository;

import org.example.cash_app.logger.TransactionLogger;
import org.example.cash_app.model.Transaction;
import org.example.cash_app.model.TransactionId;
import org.example.cash_app.responses.ResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class CashRepository {

    // храним в сетах, нам порядок и сравнение не важно.
    private final Set<String> usedConfIds = new ConcurrentSkipListSet<>();
    private final List<Transaction> procTransactions = new CopyOnWriteArrayList<>();
    private final List<Transaction> completedTransactions = new CopyOnWriteArrayList<>();
    private final List<Transaction> failedTransactions = new CopyOnWriteArrayList<>();


    public String getConfId(Transaction transactionReceived) {
        // генерируем 4-х значный уникальный айди.
        String confID = generateId();
        usedConfIds.add(confID);
        // добавляем к ней уникальный айди и заносим в лист выполняющихся тразакций.
        transactionReceived.getID().setOperationId(confID);
        procTransactions.add(transactionReceived);
        // логгируем.
        TransactionLogger.logProcessedTrans(transactionReceived, TransactionLogger.LogType.PENDING);
        // возвращаем айди по цепочке клиенту.
        return confID;
    }

    public ResponseEntity<Map<String, Object>> confirmOperation(TransactionId id) {
        // сравниваем вернувшийся айди и "код"(он всегда 0000) с хранящимися в нашем репозитории.
        String idReq = id.getOperationId();
        String codeReq = id.getCode();
        //
        Transaction transactionToConfirm = procTransactions.stream()
                .filter(t -> Objects.equals(t.getID().getOperationId(), idReq))
                .findFirst()
                .orElse(null);
        //
        procTransactions.remove(transactionToConfirm);
        //
        if (transactionToConfirm != null) {
            if (transactionToConfirm.getID().getCode().equals(codeReq)) {
                // Если транзакция найдена и код совпадает.
                completedTransactions.add(transactionToConfirm);
                TransactionLogger.logProcessedTrans(transactionToConfirm, TransactionLogger.LogType.SUCCESS);
                return ResponseGenerator.generateResponse(idReq, ResponseGenerator.MSG_200, HttpStatus.OK);   // успешный перевод.
            } else {
                failedTransactions.add(transactionToConfirm);
                TransactionLogger.logProcessedTrans(transactionToConfirm, TransactionLogger.LogType.FAILED);
                return ResponseGenerator.generateResponse(idReq, ResponseGenerator.MSG_500, HttpStatus.INTERNAL_SERVER_ERROR);  // код не совпал.
            }
        } else {
            TransactionLogger.logUnknown(TransactionLogger.LogType.UNKNOWN);
            return ResponseGenerator.generateResponse(idReq, ResponseGenerator.MSG_400, HttpStatus.BAD_REQUEST);  // id не совпал.
        }
    }


    public String generateId() {
        String newOpID;
        Random random = new Random();
        do {
            newOpID = String.valueOf(1000 + random.nextInt(9000));
        } while (usedConfIds.contains(newOpID));
        // гарантируем уникальность айди.
        return newOpID;
    }
}
