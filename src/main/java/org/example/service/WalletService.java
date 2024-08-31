package org.example.service;

import org.example.model.Wallet;
import org.example.model.Crypto;
import org.example.model.Exchange;
import org.example.repository.iRepository.WalletRepository;
import org.example.service.iService.IWalletService;

import java.math.BigDecimal;

public class WalletService implements IWalletService {
    private final WalletRepository walletRepository;
    private final Exchange exchange = Exchange.getInstance();

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet getWalletByUserId(int userId) {
        return walletRepository.findByUserId(userId);
    }

    @Override
    public void depositFiat(int userId, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        if (wallet != null) {
            wallet.addFiat(amount);
            walletRepository.save(wallet);
        }
    }

    @Override
    public String buyReserveCrypto(int userId, String cryptoSymbol, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        Crypto crypto = exchange.getCryptoBySymbol(cryptoSymbol);
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

    @Override
    public Wallet getWalletBalance(int userId) {
        return walletRepository.findByUserId(userId);
    }
}
