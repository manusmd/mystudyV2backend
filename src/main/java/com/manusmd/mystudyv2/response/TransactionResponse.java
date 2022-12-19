package com.manusmd.mystudyv2.response;

import com.manusmd.mystudyv2.model.TransactionModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransactionResponse extends TransactionModel {
    private double newBalance;

    public void copyTransaction(TransactionModel transaction){
        this.setId(transaction.getId());
        this.setStudentId(transaction.getStudentId());
        this.setValue(transaction.getValue());
        this.setDate(transaction.getDate());
    }
}
