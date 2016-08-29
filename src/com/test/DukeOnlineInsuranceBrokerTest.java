package com.test;

import com.duke.DukeOnlineInsuranceBroker;
import com.duke.InsuranceBroker;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertTrue;

/**
 * Created by samirehman on 24/08/2016.
 */
public class DukeOnlineInsuranceBrokerTest {

      InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker();

    @Test
    public void searchCarFromExistingCarListShouldReturnTheSameCar() throws Exception {
        // Searching the existing car should return the same car
        assertThat(insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).get(0).description,is("Audi / A1 2014"));
    }
    @Test
    public void searchCarNotInCarListShouldReturnNoResult() throws Exception {
        // Search for a car not in car list return empty list
        assertThat(insuranceBroker.searchForCarInsurance("Audi", "A5", 2014).size(),is(0));
    }
    @Test
    public void searchCarInCarListShouldReturnResult() throws Exception {
        // Searching an existing car in car list should return a car
        assertThat(insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).size(),is(1));
    }
    @Test
    public void newCarPremiumIsAlwaysGreaterThanOldCarPremium() throws Exception {
        // New car premium is greater than old car premium (same make model but different age)
        BigDecimal NewCarPremium, OldCarPremium;

        OldCarPremium = insuranceBroker.searchForCarInsurance("Audi", "A1", 2010).get(0).premium;
        NewCarPremium = insuranceBroker.searchForCarInsurance("Audi", "A1", 2015).get(0).premium;

        assertTrue(NewCarPremium.compareTo(OldCarPremium) > 0);

    }

}