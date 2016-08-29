package com.duke;

import java.math.BigDecimal;

/**
 * Created by samirehman on 27/08/2016.
 */
public interface Charges {
    BigDecimal getStandardAdminCharge(long elapsedTime, BigDecimal premiumAmount);

}
