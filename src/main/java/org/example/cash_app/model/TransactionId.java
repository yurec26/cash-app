package org.example.cash_app.model;

import jakarta.validation.constraints.Size;

public class TransactionId {
    @Size(max = 4, min = 4)
    private String operationId = "Не присвоен";
    private String code = "0000";  // хардкодим пасворд так как форма возвращает всегда такой пароль.

    public TransactionId() {
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "уникальный номер='" + operationId + '\'' +
                ",код='" + code + "'";
    }
}
