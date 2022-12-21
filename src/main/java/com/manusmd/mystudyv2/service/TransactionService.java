package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.model.TransactionModel;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.repository.TransactionRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.response.TransactionResponse;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    StudentRepository studentRepository;
    TransactionRepository transactionRepository;

    public CustomResponse createTransaction(TransactionModel transaction) {
        try {
            StudentModel foundStudent = StudentModel.studentExists(transaction.getStudentId(), studentRepository);
            StudentModel updatedStudent = foundStudent.bookBalanceAndSave(transaction.getValue(), studentRepository);
            TransactionModel createdTransaction = transactionRepository.save(transaction);
            TransactionResponse response = new TransactionResponse();
            response.copyTransaction(createdTransaction);
            response.setNewBalance(updatedStudent.getBalance());
            return CustomResponse.CREATED(response, "Transaction");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }

    public CustomResponse getTransaction(String id) {
        try {
            TransactionModel foundTransaction = TransactionModel.transactionExists(id, transactionRepository);
            return CustomResponse.FOUND(foundTransaction, "Transaction");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
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
            StudentModel.studentExists(studentId, studentRepository);
            List<TransactionModel> foundTransactions = transactionRepository.findByStudentId(studentId);
            return CustomResponse.FOUND_FETCHED_LIST(foundTransactions, "Transaction");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }


    public CustomResponse updateTransaction(TransactionModel transaction, String id) {
        try {
            TransactionModel foundTransaction = TransactionModel.transactionExists(id, transactionRepository);
            if (!transaction.getStudentId().equals(foundTransaction.getStudentId())) {
                return CustomResponse.PUT_NOT_ALLOWED("Can't change studentId!");
            }
            StudentModel foundStudent = StudentModel.studentExists(transaction.getStudentId(), studentRepository);
            Double delta = transaction.getValue() - foundTransaction.getValue();
            StudentModel updatedStudent = foundStudent.bookBalanceAndSave(delta, studentRepository);
            TransactionModel updatedTransaction = foundTransaction.updateAndSave(id, transaction, transactionRepository);

            TransactionResponse response = new TransactionResponse();
            response.copyTransaction(updatedTransaction);
            response.setNewBalance(updatedStudent.getBalance());

            return CustomResponse.OK_PUT(response, "Transaction");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }

    public CustomResponse deleteTransaction(String id) {
        try {
            TransactionModel foundTransaction = TransactionModel.transactionExists(id, transactionRepository);
            StudentModel foundStudent = StudentModel.studentExists(foundTransaction.getStudentId(), studentRepository);
            Double correctionValue = -foundTransaction.getValue();
            StudentModel updatedStudent = foundStudent.bookBalanceAndSave(correctionValue, studentRepository);
            transactionRepository.deleteById(id);
            return CustomResponse.OK_DELETE("Transaction", id, "New balance: " + updatedStudent.getBalance());
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }
}
