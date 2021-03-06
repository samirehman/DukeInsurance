package com.duke;

import java.math.BigDecimal;

public class ProductionChargeCalculationRules implements Charges {

    static BigDecimal STANDARD_ADMIN_CHARGE;

    private static final Charges INSTANCE = new ProductionChargeCalculationRules();

    public static Charges getInstance(){
        return INSTANCE;
    }

    @Override
    public BigDecimal getStandardAdminCharge(long elapsedTime, BigDecimal premiumAmount) {

        STANDARD_ADMIN_CHARGE = standardAdminChargeCalculationRule(elapsedTime, premiumAmount);
        return STANDARD_ADMIN_CHARGE;
    }

    private BigDecimal getAdminChargeAmount(BigDecimal pctFactor, BigDecimal originalAmount, BigDecimal defaultAdminCharge){

        BigDecimal  pctPremiumAmount = BigDecimal.valueOf(0.0);

        pctPremiumAmount = originalAmount.multiply(pctFactor); // calculate the percentage amount using (originalAmount * pctFactor)

        if (pctPremiumAmount.compareTo(defaultAdminCharge) == 1 ) {
            return pctPremiumAmount;
        }
        else
            return defaultAdminCharge;
    }

    private BigDecimal standardAdminChargeCalculationRule(long elapsedTimeInMills, BigDecimal premiumAmount){

        final long QUOTE_AGE_THREE_MILLIS    =  3 * 60 * 1000; //  3 minutes
        final long QUOTE_AGE_TEN_MILLIS      = 10 * 60 * 1000; // 10 minutes
        final long QUOTE_AGE_FIFTEEN_MILLIS  = 15 * 60 * 1000; // 15 minutes

        BigDecimal  adminCharge = BigDecimal.valueOf(0.00);

        // Admin charge calculation rules based on 

        if (elapsedTimeInMills <= QUOTE_AGE_THREE_MILLIS) // If confirmed within 3 minutes no charge
        {
            adminCharge = BigDecimal.valueOf(0.00);
        }
        else if (elapsedTimeInMills > QUOTE_AGE_THREE_MILLIS && elapsedTimeInMills <= QUOTE_AGE_TEN_MILLIS) { // Under 10 minutes £15 or 5% whichever is greater

            adminCharge = getAdminChargeAmount(BigDecimal.valueOf(0.05),premiumAmount,BigDecimal.valueOf(1500,2));

        } else {
            if (elapsedTimeInMills > QUOTE_AGE_TEN_MILLIS && elapsedTimeInMills < QUOTE_AGE_FIFTEEN_MILLIS) { // Between 11 and 15 min £40 or 10% whichever is greater

                adminCharge = getAdminChargeAmount(BigDecimal.valueOf(0.10),premiumAmount,BigDecimal.valueOf(4000,2));
            }
        }
        return adminCharge;
    }

}