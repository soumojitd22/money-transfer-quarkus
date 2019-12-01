package demo.api.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import static demo.api.constant.AppConstants.*;

public class Transaction {
    @Pattern(regexp = "^[1-9]\\d{9}$", message = FROM_MOBILE_NUM_ERROR)
    String fromMobileNumber;
    @Pattern(regexp = "^[1-9]\\d{9}$", message = TO_MOBILE_NUM_ERROR)
    String toMobileNumber;
    @Min(value = 1, message = MIN_AMT_TRANSFER_ERROR)
    long amount;

    public String getFromMobileNumber() {
        return fromMobileNumber;
    }

    public void setFromMobileNumber(String fromMobileNumber) {
        this.fromMobileNumber = fromMobileNumber;
    }

    public String getToMobileNumber() {
        return toMobileNumber;
    }

    public void setToMobileNumber(String toMobileNumber) {
        this.toMobileNumber = toMobileNumber;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
