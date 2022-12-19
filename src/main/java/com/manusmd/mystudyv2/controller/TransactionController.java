package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.TransactionModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TransactionController {
    TransactionService transactionService;
    @PostMapping("/Transactions")
    public ResponseEntity<CustomResponse> createTransaction(@ModelAttribute TransactionModel transaction){
        CustomResponse response = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/Transactions/{id}")
    public ResponseEntity<CustomResponse> getTransaction(@PathVariable String id){
        CustomResponse response = transactionService.getTransaction(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/Transactions")
    public ResponseEntity<CustomResponse> getAllTransactions(){
        CustomResponse response = transactionService.getAllTransactions();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/Students/{studentId}/Transactions")
    public ResponseEntity<CustomResponse> getTransactionsByStudentId(@PathVariable String studentId){
        CustomResponse response = transactionService.getTransactionsByStudentId(studentId);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PutMapping("/Transactions/{id}")
    public ResponseEntity<CustomResponse> updateTransaction(@ModelAttribute TransactionModel transaction, @PathVariable String id){
        CustomResponse response = transactionService.updateTransaction(transaction,id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/Transactions/{id}")
    public ResponseEntity<CustomResponse> deleteTransaction(@PathVariable String id){
        CustomResponse response = transactionService.deleteTransaction(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
