package org.example.service;

import org.example.model.Wallet;
import org.example.model.Crypto;
import org.example.model.Exchange;
import org.example.repository.iRepository.WalletRepository;

import java.math.BigDecimal;

public class WalletService {
    private final WalletRepository walletRepository;
    private final Exchange exchange = Exchange.getInstance();

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getWalletByUserId(int userId) {
        return walletRepository.findByUserId(userId);
    }

    public void depositFiat(int userId, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        if (wallet != null) {
            wallet.addFiat(amount);
            walletRepository.save(wallet);
        }
    }

    public void transferCrypto(int fromUserId, int toUserId, String cryptoSymbol, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findByUserId(fromUserId);
        Wallet toWallet = walletRepository.findByUserId(toUserId);
        Crypto crypto = exchange.getCryptoBySym(cryptoSymbol);

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

    public String buyExCrypto(int userId, String cryptoSymbol, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        Crypto crypto = exchange.getCryptoBySym(cryptoSymbol);
        if (wallet == null || crypto == null) return "Wallet or crypto not found";

        BigDecimal totalCost = crypto.getMarketPrice().multiply(amount);
        BigDecimal availableStock = exchange.getCryptoStock(cryptoSymbol);
        if (wallet.getFiatBalance().compareTo(totalCost) < 0 || availableStock.compareTo(amount) < 0) return "Insufficient funds or stock";

        wallet.deductFiat(totalCost);
        wallet.addCrypto(crypto, amount);
        exchange.reduceCryptoStock(cryptoSymbol, amount);
        walletRepository.save(wallet);
        return "Purchase successful";
    }

    public Wallet getWalletBalance(int userId) {
        return walletRepository.findByUserId(userId);
    }
}
