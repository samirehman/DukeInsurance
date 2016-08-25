package com.test;

import com.duke.DukeOnlineInsuranceBroker;
import com.duke.InsuranceBroker;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by samirehman on 24/08/2016.
 */
public class DukeOnlineInsuranceBrokerTest {

    @Test
    public void searchCarFromExistingCarListShouldReturnTheSameCar() throws Exception {

        InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker();
        assertThat(insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).get(0).description,is("Audi / A1 2014"));

    }
    @Test
    public void searchCarNotInCarListShouldReturnNoResult() throws Exception {

        InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker();
        assertThat(insuranceBroker.searchForCarInsurance("Audi", "A5", 2014).size(),is(0));

    }
    @Test
    public void searchCarInCarListShouldReturnResult() throws Exception {

        InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker();
        assertThat(insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).size(),is(1));

    }

    @Test
    public void newCarPremiumIsAlwaysGreaterThanOldCarPremium() throws Exception {

        BigDecimal NewCarPremium, OldCarPremium;

        InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker();

        OldCarPremium = insuranceBroker.searchForCarInsurance("Audi", "A1", 2010).get(0).premium;
        NewCarPremium = insuranceBroker.searchForCarInsurance("Audi", "A1", 2015).get(0).premium;

        //System.out.println("New Car Premium is :"+NewCarPremium+" is greater than Old Car Premium :"+OldCarPremium);
        assertTrue(NewCarPremium.compareTo(OldCarPremium)>0);

        //insuranceBroker.searchForCarInsurance("Audi", "A1", 2010).get(0).

    }
}