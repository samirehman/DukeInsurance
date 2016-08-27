package com.duke;

import java.math.BigDecimal;

/**
 * Created by samirehman on 27/08/2016.
 */
public interface IChargeCalculationRules {
    BigDecimal getStandardAdminCharge(double elapsedTime, BigDecimal premiumAmount);

    void setStandardAdminCharge(BigDecimal standardAdminCharge);

    BigDecimal getAdminChargeAmount(BigDecimal pctFactor, BigDecimal originalAmount, BigDecimal defaultAdminCharge);

    BigDecimal standardAdminChargeCalculationRule(double TimeInMin, BigDecimal premiumAmount);
}
