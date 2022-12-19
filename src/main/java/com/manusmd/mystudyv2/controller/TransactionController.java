package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.TransactionModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.response.TransactionResponse;
import com.manusmd.mystudyv2.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TransactionController {
    TransactionService transactionService;
    @PostMapping("/Transactions")
    public ResponseEntity<CustomResponse<TransactionResponse>> createTransaction(@ModelAttribute TransactionModel transaction){
        CustomResponse<TransactionResponse> response = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
