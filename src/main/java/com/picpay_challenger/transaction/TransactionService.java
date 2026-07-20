package com.picpay_challenger.transaction;

import com.picpay_challenger.wallet.Wallet;
import com.picpay_challenger.wallet.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    public Transaction create(Transaction transaction) {

        var newTransaction = transactionRepository.save(transaction);

        var wallet = walletRepository.findById(transaction.payer()).get();
        walletRepository.save(wallet.debit(transaction.value()));

        return newTransaction;

        private void validate(Transaction transaction) {

            throw new UnsupportedOperationException("Unimplemented method 'validate'");
        }

    }
}
