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
            StudentModel updatedStudent = foundStudent.get()
                    .bookBalanceAndSave(transaction.getValue(), studentRepository);
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
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<List<TransactionModel>> getTransactionsByStudentId(String studentId) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findById(studentId);
            if (foundStudent.isEmpty()) {
                return new CustomResponse<>(null, "Student " + studentId + " not found", HttpStatus.NOT_FOUND);
            }
            List<TransactionModel> foundTransactions = transactionRepository.findByStudentId(studentId);
            return new CustomResponse<>(foundTransactions, "Successfully fetched " + foundTransactions.size() + " " +
                    "transaction/s for student " + studentId, HttpStatus.OK);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public CustomResponse<TransactionModel> updateTransaction(TransactionModel transaction, String id) {
        try {
            Optional<TransactionModel> foundTransaction = transactionRepository.findById(id);
            if (foundTransaction.isEmpty()) {
                return new CustomResponse<>(null, "Transaction " + id + " not found", HttpStatus.NOT_FOUND);
            }
            if (!transaction.getStudentId().equals(foundTransaction.get().getStudentId())) {
                return new CustomResponse<>(null, "Can't change studentId! Create a POST request to book balance on " +
                        "student " + transaction.getStudentId(), HttpStatus.NOT_FOUND);
            }
            Optional<StudentModel> foundStudent = studentRepository.findById(transaction.getStudentId());
            if (foundStudent.isEmpty()) {
                return new CustomResponse<>(null, "Transaction " + id + " not found", HttpStatus.NOT_FOUND);
            }

            StudentModel updatedStudent =
                    foundStudent.get().bookBalanceAndSave(transaction.getValue() - foundTransaction.get().getValue(),
                            studentRepository);
            TransactionModel updatedTransaction = foundTransaction.get()
                    .updateAndSave(id, transaction, transactionRepository);

            TransactionResponse response = new TransactionResponse();
            response.copyTransaction(updatedTransaction);
            response.setNewBalance(updatedStudent.getBalance());
            return new CustomResponse<>(response, "Successfully updated transaction " + id, HttpStatus.OK);

        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<TransactionModel> deleteTransaction(String id) {
        try {
            Optional<TransactionModel> foundTransaction = transactionRepository.findById(id);
            if (foundTransaction.isEmpty()) {
                return new CustomResponse<>(null, "Transaction " + id + " not found", HttpStatus.NOT_FOUND);
            }
            Optional<StudentModel> foundStudent = studentRepository.findById(foundTransaction.get().getStudentId());

            if (foundStudent.isEmpty()) {
                return new CustomResponse<>(null, "Student " + foundTransaction.get().getStudentId() + " not found",
                        HttpStatus.NOT_FOUND);
            }

            StudentModel updatedStudent = foundStudent.get().bookBalanceAndSave(-foundTransaction.get().getValue(),
                    studentRepository);
            transactionRepository.deleteById(id);
            return new CustomResponse<>(null,
                    "Transaction " + id + " deleted successfully! New balance: " + updatedStudent.getBalance(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
