package org.example.cash_app.model;

import jakarta.validation.constraints.Size;


public class Transaction {
    @Size(max = 16, min = 16)
    private String cardFromNumber;
    private String cardFromValidTill;
    @Size(max = 3, min = 3)
    private String cardFromCVV;
    @Size(max = 16, min = 16)
    private String cardToNumber;
    private Amount amount;
    private TransactionId id;


    public Transaction() {
        this.id = new TransactionId();
    }

    public void setCardFromNumber(String cardFromNumber) {
        this.cardFromNumber = cardFromNumber;
    }

    public void setCardFromValidTill(String cardFromValidTill) {
        this.cardFromValidTill = cardFromValidTill;
    }

    public void setCardFromCVV(String cardFromCVV) {
        this.cardFromCVV = cardFromCVV;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void setId(TransactionId id) {
        this.id = id;
    }

    public TransactionId getID() {
        return this.id;
    }


    @Override
    public String toString() {
        return "Transaction " + id +
                " ,карта отправителя='" + cardFromNumber + '\'' +
                ", годен до'" + cardFromValidTill + '\'' +
                ", CVV'" + cardFromCVV + '\'' +
                ", карта полуателя='" + cardToNumber + '\'' +
                ", детали:" + amount;
    }
}
