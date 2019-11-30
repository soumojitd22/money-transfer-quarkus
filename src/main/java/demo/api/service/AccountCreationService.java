package demo.api.service;

import demo.api.dao.AccountDAO;
import demo.api.dto.UserAccount;
import demo.api.exception.AppError;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class AccountCreationService {
    private static final Logger LOGGER = getLogger(AccountCreationService.class);

    @Inject
    AccountDAO accountDAO;

    public void registerAccount(UserAccount userAccount) {
        if (accountDAO.doesAccountExist(userAccount.getMobileNumber())) {
            LOGGER.error("Account with mobile number {} already exists", userAccount.getMobileNumber());
            throw new AppError("Account with mobile number " + userAccount.getMobileNumber() + " already exists");
        }
        accountDAO.createAccount(userAccount);
    }
}
