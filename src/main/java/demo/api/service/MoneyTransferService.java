package demo.api.service;

import demo.api.dao.AccountDAO;
import demo.api.dao.TransactionDAO;
import demo.api.dto.Transaction;
import demo.api.exception.AppError;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class MoneyTransferService {
    private static final Logger LOGGER = getLogger(MoneyTransferService.class);

    @Inject
    TransactionDAO transactionDAO;
    @Inject
    AccountDAO accountDAO;

    public long checkAccountBalance(String mobileNumber) {
        return transactionDAO.getAccountBalance(mobileNumber);
    }

    @Transactional
    public void transferMoney(Transaction transaction) {
        long fromAccountBalance = transactionDAO.getAccountBalance(transaction.getFromMobileNumber());
        if (transaction.getAmount() > fromAccountBalance) {
            LOGGER.error("Insufficient balance to transfer money from {} to {}", transaction.getFromMobileNumber(),
                    transaction.getToMobileNumber());

            throw new AppError("Insufficient balance to transfer money. Account balance : " + fromAccountBalance);
        }
        transactionDAO.debitMoney(transaction);
        if (!accountDAO.doesAccountExist(transaction.getToMobileNumber())) {
            LOGGER.error("Account not registered for mobile number :: {}", transaction.getToMobileNumber());
            throw new AppError("Account not registered for mobile number - " + transaction.getToMobileNumber());
        }
        transactionDAO.creditMoney(transaction);
    }
}
