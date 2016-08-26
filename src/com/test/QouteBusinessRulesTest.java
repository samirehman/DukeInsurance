package com.test;

import com.duke.QouteBusinessRules;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by samirehman on 25/08/2016.
 */
public class QouteBusinessRulesTest {
    @Test
    public void quoteExpiredAfterTimePassedByMaxAgeMillis() throws Exception {

        final long QUOTE_AGE_SIXTEEN_MILLIS    =  16 * 60 * 1000; //  Sixteen minutes

        long quoteTimeStamp = System.currentTimeMillis() - QUOTE_AGE_SIXTEEN_MILLIS;
        assertThat(QouteBusinessRules.hasQuoteExpired(quoteTimeStamp),is(false));
    }
    @Test
    public void quoteValidIfCurrentTimeIsLessThanMaxAgeMillis() throws Exception {

        final long QUOTE_AGE_THREE_MILLIS    =  3 * 60 * 1000; //  3 minutes

        long quoteTimeStamp = System.currentTimeMillis() - QUOTE_AGE_THREE_MILLIS ;
        assertThat(QouteBusinessRules.hasQuoteExpired(quoteTimeStamp),is(true));
    }

}