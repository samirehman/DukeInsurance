package com.duke;

/**
 * Created by samirehman on 27/08/2016.
 */
public interface IQouteBusinessRules {
    long getMaxQuoteAgeMillis();

    boolean hasQuoteExpired(long quoteTimeStamp);

    long getQuoteAge(long quoteTimeStamp);
}
