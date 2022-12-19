package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.TransactionModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.response.TransactionResponse;
import com.manusmd.mystudyv2.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TransactionController {
    TransactionService transactionService;
    @PostMapping("/Transactions")
    public ResponseEntity<CustomResponse<TransactionResponse>> createTransaction(@ModelAttribute TransactionModel transaction){
        CustomResponse<TransactionResponse> response = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/Transactions/{id}")
    public ResponseEntity<CustomResponse<TransactionModel>> getTransaction(@PathVariable String id){
        CustomResponse<TransactionModel> response = transactionService.getTransaction(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/Transactions")
    public ResponseEntity<CustomResponse<List<TransactionModel>>> getAllTransactions(){
        CustomResponse<List<TransactionModel>> response = transactionService.getAllTransactions();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/Students/{studentId}/Transactions")
    public ResponseEntity<CustomResponse<List<TransactionModel>>> getTransactionsByStudentId(@PathVariable String studentId){
        CustomResponse<List<TransactionModel>> response = transactionService.getTransactionsByStudentId(studentId);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
