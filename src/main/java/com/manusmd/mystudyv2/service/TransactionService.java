package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.model.TransactionModel;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.repository.TransactionRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.response.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService {
    StudentRepository studentRepository;
    TransactionRepository transactionRepository;

    public CustomResponse createTransaction(TransactionModel transaction) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findById(transaction.getStudentId());
            if (foundStudent.isEmpty()) {
                return CustomResponse.NOT_FOUND("Student", transaction.getStudentId());
            }
            StudentModel updatedStudent = foundStudent.get()
                    .bookBalanceAndSave(transaction.getValue(), studentRepository);
            TransactionModel createdTransaction = transactionRepository.save(transaction);
            TransactionResponse response = new TransactionResponse();
            response.copyTransaction(createdTransaction);
            response.setNewBalance(updatedStudent.getBalance());
            return CustomResponse.CREATED(response, "Transaction");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse getTransaction(String id) {
        try {
            Optional<TransactionModel> foundTransaction = transactionRepository.findById(id);
            if (foundTransaction.isEmpty()) {
                return CustomResponse.NOT_FOUND("Transaction", id);
            }
            return CustomResponse.FOUND(foundTransaction.get(), "Transaction");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse getAllTransactions() {
        try {
            List<TransactionModel> foundTransactions = transactionRepository.findAll();
            return CustomResponse.FOUND_FETCHED_LIST(foundTransactions, "Transaction");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse getTransactionsByStudentId(String studentId) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findById(studentId);
            if (foundStudent.isEmpty()) {
                return CustomResponse.NOT_FOUND("Student", studentId);
            }
            List<TransactionModel> foundTransactions = transactionRepository.findByStudentId(studentId);
            return CustomResponse.FOUND_FETCHED_LIST(foundTransactions, "Transaction");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }


    public CustomResponse updateTransaction(TransactionModel transaction, String id) {
        try {
            Optional<TransactionModel> foundTransaction = transactionRepository.findById(id);
            if (foundTransaction.isEmpty()) {
                return CustomResponse.NOT_FOUND("Transaction", id);
            }
            if (!transaction.getStudentId().equals(foundTransaction.get().getStudentId())) {
                return CustomResponse.PUT_NOT_ALLOWED("Can't change studentId!");
            }
            Optional<StudentModel> foundStudent = studentRepository.findById(transaction.getStudentId());
            if (foundStudent.isEmpty()) {
                return CustomResponse.NOT_FOUND("Transaction", id);
            }
            Double delta = transaction.getValue() - foundTransaction.get().getValue();
            StudentModel updatedStudent = foundStudent.get().bookBalanceAndSave(delta, studentRepository);
            TransactionModel updatedTransaction = foundTransaction.get()
                    .updateAndSave(id, transaction, transactionRepository);

            TransactionResponse response = new TransactionResponse();
            response.copyTransaction(updatedTransaction);
            response.setNewBalance(updatedStudent.getBalance());

            return CustomResponse.OK_PUT(response, "Transaction");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse deleteTransaction(String id) {
        try {
            Optional<TransactionModel> foundTransaction = transactionRepository.findById(id);
            if (foundTransaction.isEmpty()) {
                return CustomResponse.NOT_FOUND("Transaction", id);
            }
            Optional<StudentModel> foundStudent = studentRepository.findById(foundTransaction.get().getStudentId());
            if (foundStudent.isEmpty()) {
                return CustomResponse.NOT_FOUND("Student", foundTransaction.get().getStudentId());
            }
            Double correctionValue = -foundTransaction.get().getValue();
            StudentModel updatedStudent = foundStudent.get().bookBalanceAndSave(correctionValue, studentRepository);
            transactionRepository.deleteById(id);
            return CustomResponse.OK_DELETE("Transaction", id, "New balance: " + updatedStudent.getBalance());
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
}
