package demo.api.dao;

import demo.api.dto.UserAccount;
import demo.api.exception.AppError;
import io.agroal.api.AgroalDataSource;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static demo.api.constant.AppConstants.ACCOUNT_CHECK_SQL;
import static demo.api.constant.AppConstants.CREATE_ACCOUNT_SQL;
import static java.lang.Long.parseLong;
import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class AccountDAO {
    private static final Logger LOGGER = getLogger(AccountDAO.class);

    @Inject
    AgroalDataSource dataSource;

    public boolean doesAccountExist(String mobileNumber) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(ACCOUNT_CHECK_SQL)) {

            boolean isAccountPresent = false;
            LOGGER.info("Checking if account with mobile number {} exists", mobileNumber);
            preparedStatement.setLong(1, parseLong(mobileNumber));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) != 0)
                    isAccountPresent = true;

            }
            return isAccountPresent;
        } catch (SQLException ex) {
            LOGGER.error("Error occurred to fetch data from Accounts table", ex);
            throw new AppError("Error occurred to fetch data from DB - " + ex.getMessage());
        }
    }

    public void createAccount(UserAccount userAccount) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CREATE_ACCOUNT_SQL)) {

            LOGGER.info("Inserting Account data into Accounts table");
            preparedStatement.setLong(1, parseLong(userAccount.getMobileNumber()));
            preparedStatement.setLong(2, userAccount.getAccountBalance());
            preparedStatement.execute();
            LOGGER.info("Account data successfully inserted into Account table");
        } catch (SQLException ex) {
            LOGGER.error("Error occurred to insert data into Accounts table", ex);
            throw new AppError("Error occurred to insert data in DB - " + ex.getMessage());
        }
    }
}
