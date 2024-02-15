package com.swiggy.wallet.controllers;

import com.swiggy.wallet.entities.Wallet;
import com.swiggy.wallet.exceptions.AuthenticationFailedException;
import com.swiggy.wallet.requestModels.WalletRequestModel;
import com.swiggy.wallet.responseModels.WalletResponseModel;
import com.swiggy.wallet.exceptions.InsufficientBalanceException;
import com.swiggy.wallet.exceptions.InvalidAmountException;
import com.swiggy.wallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Wallet> create(){
        Wallet returnedWallet = walletService.create(new Wallet());

        return new ResponseEntity<>(returnedWallet, HttpStatus.CREATED);
    }

    @PutMapping("/deposit")
    public ResponseEntity<Wallet> deposit(@RequestBody WalletRequestModel requestModel) throws InvalidAmountException, AuthenticationFailedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Wallet returnedWallet = walletService.deposit(username, requestModel);

        return new ResponseEntity<>(returnedWallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<Wallet> withdraw(@RequestBody WalletRequestModel requestModel) throws InsufficientBalanceException, InvalidAmountException, AuthenticationFailedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Wallet returnedWallet = walletService.withdraw(username, requestModel);

        return new ResponseEntity<>(returnedWallet, HttpStatus.ACCEPTED);
    }

    @GetMapping("")
    public ResponseEntity<List<WalletResponseModel>> wallets(){
        List<WalletResponseModel> responseWallets = walletService.getAllWallets();

        return new ResponseEntity<>(responseWallets, HttpStatus.OK);
    }
}
