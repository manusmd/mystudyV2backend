package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.model.TransactionModel;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.repository.TransactionRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.response.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService {
    StudentRepository studentRepository;
    TransactionRepository transactionRepository;

    public CustomResponse<TransactionResponse> createTransaction(TransactionModel transaction) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findById(transaction.getStudentId());
            if (foundStudent.isEmpty()) {
                return new CustomResponse<>(null, "Student " + transaction.getStudentId() + " not found",
                        HttpStatus.NOT_FOUND);
            }
            foundStudent.get().bookBalance(transaction.getValue());
            StudentModel updatedStudent = studentRepository.save(foundStudent.get());
            TransactionModel createdTransaction = transactionRepository.save(transaction);
            TransactionResponse response = new TransactionResponse();
            response.copyTransaction(createdTransaction);
            response.setNewBalance(updatedStudent.getBalance());
            return new CustomResponse<>(response, "Successfully created transaction", HttpStatus.OK);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<TransactionModel> getTransaction(String id) {
        try {
            Optional<TransactionModel> foundTransaction = transactionRepository.findById(id);
            if (foundTransaction.isEmpty()) {
                return new CustomResponse<>(null, "Transaction " + id + " not found", HttpStatus.NOT_FOUND);
            }
            return new CustomResponse<>(foundTransaction.get(), "Transaction found", HttpStatus.OK);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<List<TransactionModel>> getAllTransactions() {
        try {
            List<TransactionModel> foundTransactions = transactionRepository.findAll();
            return new CustomResponse<>(foundTransactions, "Successfully fetched " + foundTransactions.size() + " " +
                    "transaction/s", HttpStatus.OK);
        } catch (Exception e){
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
