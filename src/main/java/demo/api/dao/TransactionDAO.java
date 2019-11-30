package demo.api.dao;

import demo.api.dto.Transaction;
import demo.api.exception.AppError;
import io.agroal.api.AgroalDataSource;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static demo.api.constant.AppConstants.*;
import static java.lang.Long.parseLong;
import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class TransactionDAO {
    private static final Logger LOGGER = getLogger(TransactionDAO.class);

    @Inject
    AgroalDataSource dataSource;

    public long fetchAccountBalance(String mobileNumber) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(FETCH_BALANCE_SQL)) {

            LOGGER.info("Fetching Account data from Accounts table for mobile number :: {}", mobileNumber);
            preparedStatement.setLong(1, parseLong(mobileNumber));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long balance = resultSet.getLong(1);
                    LOGGER.info("Account balance successfully fetched for mobile number :: {}", mobileNumber);
                    return balance;
                } else {
                    LOGGER.error("Account not registered for mobile number :: {}", mobileNumber);
                    throw new AppError("Account not registered for mobile number - " + mobileNumber);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("Error occurred to fetch data from Accounts table", ex);
            throw new AppError("Error occurred to fetch data from DB - " + ex.getMessage());
        }
    }

    public void debitMoney(Transaction transaction) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DEBIT_MONEY_SQL)) {

            LOGGER.info("Debiting money from account (Mobile number - {})", transaction.getFromMobileNumber());
            preparedStatement.setDouble(1, transaction.getAmount());
            preparedStatement.setLong(2, parseLong(transaction.getFromMobileNumber()));
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 1) {
                LOGGER.info("Account balance updated for mobile number :: {}", transaction.getFromMobileNumber());
            } else {
                LOGGER.error("Failed to update account balance for mobile number :: {}", transaction.getFromMobileNumber());
                throw new AppError("Failed to update account balance for mobile number " + transaction.getFromMobileNumber());
            }
        } catch (SQLException ex) {
            LOGGER.error("Error occurred to update balance in Accounts table", ex);
            throw new AppError("Error occurred to update balance - " + ex.getMessage());
        }
    }

    public void creditMoney(Transaction transaction) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CREDIT_MONEY_SQL)) {

            LOGGER.info("Crediting money to account (Mobile number - {})", transaction.getToMobileNumber());
            preparedStatement.setDouble(1, transaction.getAmount());
            preparedStatement.setLong(2, parseLong(transaction.getToMobileNumber()));
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 1) {
                LOGGER.info("Account balance updated for mobile number :: {}", transaction.getToMobileNumber());
            } else {
                LOGGER.error("Failed to update account balance for mobile number :: {}", transaction.getToMobileNumber());
                throw new AppError("Failed to update account balance for mobile number " + transaction.getToMobileNumber());
            }
        } catch (SQLException ex) {
            LOGGER.error("Error occurred to update balance in Accounts table", ex);
            throw new AppError("Error occurred to update balance - " + ex.getMessage());
        }
    }
}
