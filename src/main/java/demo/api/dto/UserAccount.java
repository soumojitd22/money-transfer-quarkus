package demo.api.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import static demo.api.constant.AppConstants.INIT_MIN_AMT_ERROR;
import static demo.api.constant.AppConstants.MOBILE_NUM_ERROR;

public class UserAccount {
    @Pattern(regexp = "^[1-9]\\d{9}$", message = MOBILE_NUM_ERROR)
    String mobileNumber;
    @Min(value = 100, message = INIT_MIN_AMT_ERROR)
    long accountBalance;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(long accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "mobileNumber='" + mobileNumber + '\'' +
                ", accountBalance=" + accountBalance +
                '}';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserAccount that = (UserAccount) o;
        return getMobileNumber().equals(that.getMobileNumber());
    }

    @Override
    public int hashCode() {
        return getMobileNumber().hashCode();
    }
}
