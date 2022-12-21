package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.TransactionRepository;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Document("transactions")
public class TransactionModel {
    @Id
    private String id;
    private String studentId;
    private Double value;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;


    public TransactionModel updateAndSave(String id, TransactionModel transaction, TransactionRepository transactionRepository){
        TransactionModel newTransaction = new TransactionModel();
        newTransaction.setId(id);
        newTransaction.setStudentId(transaction.getStudentId());
        newTransaction.setValue(transaction.getValue());
        newTransaction.setDate(transaction.getDate());
        transactionRepository.save(newTransaction);
        return newTransaction;
    }

    public static TransactionModel transactionExists(String id, TransactionRepository transactionRepository) throws ResourceNotFound {
        Optional<TransactionModel> foundTransaction = transactionRepository.findById(id);
        if (foundTransaction.isEmpty()) {
            throw new ResourceNotFound("Transaction",id);
        }
        return foundTransaction.get();
    }

}
