package demo.api.endpoint;

import demo.api.dto.APIResponse;
import demo.api.dto.Transaction;
import demo.api.dto.UserAccount;
import demo.api.exception.AppError;
import demo.api.service.AccountCreationService;
import demo.api.service.MoneyTransferService;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.function.Consumer;

import static demo.api.constant.AppConstants.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * REST API class exposing 3 REST endpoints.
 * <p>
 * 1. /register-account
 * <p>
 * 2. /check-balance
 * <p>
 * 3. /transfer-money
 * <p></p>
 * All the endpoints accepts "application/json" and produces "application/json"
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MoneyTransferAPI {
    private static final Logger LOGGER = getLogger(MoneyTransferAPI.class);

    @Inject
    AccountCreationService accountCreationService;
    @Inject
    MoneyTransferService moneyTransferService;

    /**
     * REST endpoint :: /register-account
     * <p>
     * This is a HTTP POST endpoint which accepts JSON data of type UserAccount
     *
     * @param userAccount Input request - UserAccount
     * @return APIResponse
     */
    @POST
    @Path("register-account")
    public APIResponse registerUserAccount(@Valid UserAccount userAccount) {
        LOGGER.info("Input request :: {}", userAccount);
        return generateResponse(response -> {
            accountCreationService.registerAccount(userAccount);
            response.setStatus(SUCCESS_STATUS);
            response.setDescription(ACC_REG_SUCCESS);
        });
    }

    /**
     * REST endpoint :: /check-balance
     * <p>
     * This is a HTTP POST endpoint which accepts JSON data of mobile number
     *
     * @param mobileNumber Account Id - Mobile number
     * @return APIResponse
     */
    @POST
    @Path("check-balance")
    public APIResponse checkAccountBalance(@Valid @Pattern(regexp = "^[1-9]\\d{9}$", message = MOBILE_NUM_ERROR) String mobileNumber) {
        LOGGER.info("Input account id :: {}", mobileNumber);
        return generateResponse(response -> {
            long balance = moneyTransferService.checkAccountBalance(mobileNumber);
            response.setStatus(SUCCESS_STATUS);
            response.setDescription("Account balance of mobile number " + mobileNumber + " :: " + balance);
        });
    }

    /**
     * REST endpoint :: /transfer-money
     * <p>
     * This is a HTTP POST endpoint which accepts JSON data of type Transaction
     *
     * @param transaction Input request - Transaction
     * @return APIResponse
     */
    @POST
    @Path("transfer-money")
    public APIResponse transferMoney(@Valid Transaction transaction) {
        LOGGER.info("Input request :: {}", transaction);
        return generateResponse(response -> {
            moneyTransferService.transferMoney(transaction);
            response.setStatus(SUCCESS_STATUS);
            response.setDescription("Amount " + transaction.getAmount() + " transferred from "
                    + transaction.getFromMobileNumber() + " to " + transaction.getToMobileNumber());

        });
    }

    private APIResponse generateResponse(Consumer<APIResponse> tryFunction) {
        APIResponse response = new APIResponse();
        try {
            tryFunction.accept(response);
        } catch (AppError appError) {
            response.setStatus(FAILURE_STATUS);
            response.setDescription(appError.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Unexpected error occurred", ex);
            response.setStatus(FAILURE_STATUS);
            response.setDescription(GENERIC_FAILURE);
        }
        return response;
    }
}
