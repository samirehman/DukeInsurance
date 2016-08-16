package com.duke;

import com.duke.insurance.Purchase;
import com.duke.insurance.ProductionPurchaseCompletionSystem;
import com.duke.search.Policy;
import com.duke.search.Quote;
import com.duke.search.ProductionQuotingSystem;

import java.math.BigDecimal;
import java.util.*;

public class DukeOnlineInsuranceBroker implements InsuranceBroker {

    private static BigDecimal STANDARD_ADMIN_CHARGE;
    private static long MAX_QUOTE_AGE_MILLIS;
    private Map<UUID, Quote> quotes;

    public DukeOnlineInsuranceBroker() {
        quotes = new HashMap<UUID, Quote>();
        setStandardAdminCharge(new BigDecimal(10));
        setMaxQuoteAgeMillis(15 * 60 * 1000);
    }

    private static BigDecimal getStandardAdminCharge() {
        return STANDARD_ADMIN_CHARGE;
    }

    public static void setStandardAdminCharge(BigDecimal standardAdminCharge) {
        STANDARD_ADMIN_CHARGE = standardAdminCharge;
    }

    public static long getMaxQuoteAgeMillis() {
        return MAX_QUOTE_AGE_MILLIS;
    }

    public static void setMaxQuoteAgeMillis(long maxQuoteAgeMillis) {
        MAX_QUOTE_AGE_MILLIS = maxQuoteAgeMillis;
    }


    @Override
    public List<Policy> searchForCarInsurance(String make, String model, int year) {

        List<Policy> searchResults;
        searchResults = ProductionQuotingSystem.getInstance().searchFor(make, model, year);
        for (Policy policy : searchResults) {
            quotes.put(policy.id, new Quote(policy, System.currentTimeMillis()));
        }
        return searchResults;
    }

    @Override
    public void confirmPurchase(UUID id, String userAuthToken) {

        if (!quotes.containsKey(id)) {
            throw new NoSuchElementException("Offer ID is invalid");
        }

        Quote quote = quotes.get(id);

        long timeNow = System.currentTimeMillis();
        long quoteAge = timeNow - quote.timestamp;

        if (quoteAge > getMaxQuoteAgeMillis()) {
            throw new IllegalStateException("Quote expired, please search again.");
        }

        Purchase completePurchase;
        BigDecimal totalPrice = quote.policy.premium.add(getStandardAdminCharge());

        completePurchase = new Purchase(totalPrice, quote, timeNow, userAuthToken);
        ProductionPurchaseCompletionSystem.getInstance().process(completePurchase);
    }

}