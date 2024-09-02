package org.example.service;

import org.example.model.Wallet;
import org.example.model.Crypto;
import org.example.model.Exchange;
import org.example.repository.iRepository.WalletRepository;
import org.example.service.exception.FailedTransactionEx;
import org.example.service.exception.InvalidCryptoEx;
import org.example.service.exception.InvalidWalletEx;

import java.math.BigDecimal;

public class WalletService {
    private final WalletRepository walletRepository;
    private final Exchange exchange = Exchange.getInstance();

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getWalletByUser(int userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) throw new InvalidWalletEx();
        return wallet;
    }

    public void depositFiat(int userId, BigDecimal amount) {
        Wallet wallet = getWalletByUser(userId);
        wallet.addFiat(amount);
        saveWallet(wallet);
    }

    public void transferCrypto(int fromUserId, int toUserId, String cryptoSymbol, BigDecimal amount) {
        Wallet fromWallet = getWalletByUser(fromUserId);
        Wallet toWallet = getWalletByUser(toUserId);
        Crypto crypto = exchange.getCryptoBySymbol(cryptoSymbol);
        if (crypto == null) throw new InvalidCryptoEx();

        fromWallet.deductCrypto(crypto, amount);
        toWallet.addCrypto(crypto, amount);

        saveWallet(fromWallet);
        saveWallet(toWallet);
    }

    public void transferFiat(int fromUserId, int toUserId, BigDecimal amount) {
        Wallet fromWallet = getWalletByUser(fromUserId);
        Wallet toWallet = getWalletByUser(toUserId);

        fromWallet.deductFiat(amount);
        toWallet.addFiat(amount);

        saveWallet(fromWallet);
        saveWallet(toWallet);
    }

    public void buyExchangeCrypto(int userId, String cryptoSymbol, BigDecimal amount) {
        Wallet wallet = getWalletByUser(userId);
        Crypto crypto = exchange.getCryptoBySymbol(cryptoSymbol);
        if (crypto == null) throw new InvalidCryptoEx();

        BigDecimal totalCost = crypto.getMarketPrice().multiply(amount);
        BigDecimal availableStock = exchange.getCryptoStock(cryptoSymbol);
        if (wallet.getFiatBalance().compareTo(totalCost) < 0 || availableStock.compareTo(amount) < 0) {
            throw new FailedTransactionEx("Insufficient funds");
        }
        wallet.deductFiat(totalCost);
        wallet.addCrypto(crypto, amount);
        exchange.reduceCryptoStock(cryptoSymbol, amount);
        saveWallet(wallet);
    }

    private void saveWallet(Wallet wallet) {
        walletRepository.save(wallet);
    }
}
