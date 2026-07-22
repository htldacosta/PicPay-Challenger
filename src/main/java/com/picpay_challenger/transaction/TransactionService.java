package com.picpay_challenger.transaction;

import com.picpay_challenger.notification.Notification;
import com.picpay_challenger.wallet.Wallet;
import com.picpay_challenger.wallet.WalletRepository;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository,
                              WalletRepository walletRepository,
                              NotificationService notificationService,
                              AuthorizerService authorizerService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.notificationService = notificationService;
        this.authorizerService = authorizerService;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        validate(transaction);

        var newTransaction = transactionRepository.save(transaction);

        var walletpayer = walletRepository.findById(transaction.payer()).get();
        var walletPayee = walletRepository.findById(transaction.payee().get();
        walletRepository.save(walletpayer.debit(transaction.value()));
        walletRepository.save(walletPayee.credit(transaction.value()));

        authorizerService.authorize(transaction);
        notificationService.notify(newTransaction);

        return newTransaction;


    }

    private void validate(Transaction transaction) {
        LOGGER.info("validating transaction {}...", transaction);

        walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                        .map(
                                payer -> payer.type() == walletType.COMUM.getValue() &&
                                        payer.balance().compareTo(transaction.value()) >= 0 &&
                                        !payer.id().equals(transaction.payee() ? true : null)
                                                .orElseThrow(() -> new InvalidTransactionException(
                                                        "Invalid transaction - " + transaction
                                                )))
                        .orElseThrow(() -> new InvalidTransactionException(
                                "Invalid transaction - " + transaction));

    }

    public List<Transaction> list() {
        return transactionRepository.findAll();
    }
}
