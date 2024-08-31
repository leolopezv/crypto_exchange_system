package org.example.service;

import org.example.model.Crypto;
import org.example.model.Exchange;
import org.example.model.Wallet;
import org.example.repository.iRepository.WalletRepository;
import org.example.service.iService.ITransferService;

import java.math.BigDecimal;

public class TransferService implements ITransferService {
    private final WalletRepository walletRepository;
    private final Exchange exchange = Exchange.getInstance();

    public TransferService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void transferCrypto(int fromUserId, int toUserId, String cryptoSymbol, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findByUserId(fromUserId);
        Wallet toWallet = walletRepository.findByUserId(toUserId);
        Crypto crypto = exchange.getCryptoBySymbol(cryptoSymbol);

        fromWallet.deductCrypto(crypto, amount);
        toWallet.addCrypto(crypto, amount);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }

    @Override
    public void transferFiat(int fromUserId, int toUserId, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findByUserId(fromUserId);
        Wallet toWallet = walletRepository.findByUserId(toUserId);

        fromWallet.deductFiat(amount);
        toWallet.addFiat(amount);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }
}
