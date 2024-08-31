package org.example.service;

import org.example.model.Crypto;
import org.example.model.Exchange;
import org.example.model.Wallet;
import org.example.repository.iRepository.WalletRepository;

import java.math.BigDecimal;

public class TransferService {
    private final WalletRepository walletRepository;
    private final Exchange exchange;

    public TransferService(WalletRepository walletRepository, Exchange exchange) {
        this.walletRepository = walletRepository;
        this.exchange = exchange;
    }

    public void transferCrypto(int fromUserId, int toUserId, String cryptoSymbol, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findByUserId(fromUserId);
        Wallet toWallet = walletRepository.findByUserId(toUserId);
        Crypto crypto = exchange.getCryptoBySymbol(cryptoSymbol);

        fromWallet.deductCrypto(crypto, amount);
        toWallet.addCrypto(crypto, amount);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }

    public void transferFiat(int fromUserId, int toUserId, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findByUserId(fromUserId);
        Wallet toWallet = walletRepository.findByUserId(toUserId);

        fromWallet.deductFiat(amount);
        toWallet.addFiat(amount);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }
}
