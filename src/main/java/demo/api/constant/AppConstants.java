package demo.api.constant;

public final class AppConstants {
    public static final String SUCCESS_STATUS = "Success";
    public static final String FAILURE_STATUS = "Failure";

    public static final String ACC_REG_SUCCESS = "Account registered successfully";
    public static final String GENERIC_FAILURE = "Sorry to behave unexpectedly!! We are trying to correct us";

    public static final String MOBILE_NUM_ERROR = "Mobile number must be of 10 digits only";
    public static final String INIT_MIN_AMT_ERROR = "Initial deposited amount must be minimum 100";
    public static final String FROM_MOBILE_NUM_ERROR = "From mobile number must be of 10 digits only";
    public static final String TO_MOBILE_NUM_ERROR = "To mobile number must be of 10 digits only";
    public static final String MIN_AMT_TRANSFER_ERROR = "Minimum amount to be transferred is 1";

    public static final String ACCOUNT_CHECK_SQL = "SELECT COUNT(*) FROM ACCOUNTS WHERE ACCOUNT_ID = ?";
    public static final String CREATE_ACCOUNT_SQL = "INSERT INTO ACCOUNTS VALUES (?, ?)";
    public static final String FETCH_BALANCE_SQL = "SELECT ACCOUNT_BALANCE FROM ACCOUNTS WHERE ACCOUNT_ID = ?";
    public static final String DEBIT_MONEY_SQL = "UPDATE ACCOUNTS SET ACCOUNT_BALANCE = (ACCOUNT_BALANCE - ?) WHERE ACCOUNT_ID = ?";
    public static final String CREDIT_MONEY_SQL = "UPDATE ACCOUNTS SET ACCOUNT_BALANCE = (ACCOUNT_BALANCE + ?) WHERE ACCOUNT_ID = ?";

    private AppConstants() {
    }
}
