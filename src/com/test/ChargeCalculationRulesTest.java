package com.test;

//import com.duke.DukeOnlineInsuranceBroker;
import com.duke.ChargeCalculationRules;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by samirehman on 25/08/2016.
 */
public class ChargeCalculationRulesTest {

    double timeInMills;
    BigDecimal premiumAmount,result ;

    ChargeCalculationRules chargeCalcRules = new ChargeCalculationRules();


    @Test
    public void adminChargeAmountWhenQuoteAgeIs3MinsORLess() throws Exception

    {
        // No charge if less than 3 mins
        timeInMills = 3*60*1000;
        assertThat(chargeCalcRules.getStandardAdminCharge(timeInMills,  premiumAmount.valueOf(100)), is(result.valueOf(0.0)));

    }
    @Test
    public void adminChargeAmountWhenQuoteAgeBtw3And10MinsPremiumLessThan5Percent() throws Exception

    {
        // Confirmation under 10 minutes And 5% of Premium is less than £15
        timeInMills = 4*60*1000;
        assertThat(chargeCalcRules.getStandardAdminCharge(timeInMills,  premiumAmount.valueOf(100.0)), is(result.valueOf(1500,2)));
        timeInMills = 10*60*1000;
        assertThat(chargeCalcRules.getStandardAdminCharge(timeInMills,  premiumAmount.valueOf(100.0)), is(result.valueOf(1500,2)));

    }
    @Test
    public void adminChargeAmountWhenQuoteAgeBtw3And10MinsPremiumGreaterThan5Percent() throws Exception

    {
        // Confirmation under 10 minutes And 5% of  Premium is greater than £15
        timeInMills = 4*60*1000;
        assertThat(chargeCalcRules.getStandardAdminCharge(timeInMills,  premiumAmount.valueOf(500)), is(result.valueOf(2500,2)));
        timeInMills = 10*60*1000;
        assertThat(chargeCalcRules.getStandardAdminCharge(timeInMills,  premiumAmount.valueOf(500)), is(result.valueOf(2500,2)));

    }

    @Test
    public void adminChargeAmountWhenQuoteAgeBtw11And15MinsPremiumLessThan10Percent() throws Exception

    {
        // Between 10 and 15 min £40 or 10% whichever is greater
        timeInMills = 11*60*1000;
        assertThat(chargeCalcRules.getStandardAdminCharge(timeInMills,  premiumAmount.valueOf(100.0)), is(result.valueOf(4000,2)));
        timeInMills = 14*60*1000;
        assertThat(chargeCalcRules.getStandardAdminCharge(timeInMills,  premiumAmount.valueOf(100.0)), is(result.valueOf(4000,2)));

    }
    @Test
    public void adminChargeAmountWhenQuoteAgeBtw11And15MinsPremiumGreaterThan10Percent() throws Exception

    {
        // Between 11 and 15 min £40 or 10% whichever is greater
        timeInMills = 14*60*1000;
        assertThat(chargeCalcRules.getStandardAdminCharge(timeInMills,  premiumAmount.valueOf(500.0)), is(result.valueOf(5000,2)));
        timeInMills = 11*60*1000;
        assertThat(chargeCalcRules.getStandardAdminCharge(timeInMills,  premiumAmount.valueOf(500.0)), is(result.valueOf(5000,2)));

    }

    /*
    @Test
    public void returnDefaultChargeAmountWhenOriginalAmountPctIsLessThanDefaulChargeAmount(){

            //(BigDecimal pctFactor, BigDecimal originalAmount, BigDecimal defaultAdminCharge){
       assertthat(chargeCalcRules.getAdminChargeAmount(BigDecimal.valueOf(0.10), BigDecimal.valueOf(100), BigDecimal.valueOf(100)),is(BigDecimal.valueOf(10))));

    }
    */

/*
        BigDecimal premiumAmount,result;
        long timeInMills;


        DukeOnlineInsuranceBroker insuranceBrokerTest = new DukeOnlineInsuranceBroker();

        // No charge if less than 3 mins
        timeInMills = 3*60*1000;
        result = new BigDecimal("0.0");

        assertThat(insuranceBrokerTest.CalcAdminCharge(timeInMills, BigDecimal.valueOf(100)),is(result));

        // Between 3 and 10 min £15 or 5% whichever is greater

        timeInMills = 4*60*1000;

        assertThat(insuranceBrokerTest.CalcAdminCharge(timeInMills, BigDecimal.valueOf(100)),is(result.valueOf(1500,2)));
        assertThat(insuranceBrokerTest.CalcAdminCharge(timeInMills, BigDecimal.valueOf(1000)),is(result.valueOf(5000,2)));

        // Between 11 and 15 min £40 or 10% whichever is greater

        timeInMills = 13*60*1000;

        assertThat(insuranceBrokerTest.CalcAdminCharge(timeInMills, BigDecimal.valueOf(100)),is(result.valueOf(4000,2)));
        assertThat(insuranceBrokerTest.CalcAdminCharge(timeInMills, BigDecimal.valueOf(2000)),is(result.valueOf(20000,2)));

        //assertThat(insuranceBrokerTest.CalcAdminCharge(13,100),is(40.0));
        //assertThat(insuranceBrokerTest.CalcAdminCharge(11,2000),is(200.0));

    }

*/
}