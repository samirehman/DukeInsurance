import com.duke.*;
import com.duke.insurance.ProductionPurchaseCompletionSystem;
import com.duke.search.Policy;
import com.duke.search.ProductionQuotingSystem;

import java.math.BigDecimal;
import java.util.List;

public class ExampleClient {

    public static void main(String[] args) throws Exception {

        PurchaseService purchaseService = ProductionPurchaseCompletionSystem.getInstance();
        QuotingSystem quotingSystem = ProductionQuotingSystem.getInstance();
        Charges chargeCalculationRules = ProductionChargeCalculationRules.getInstance();
        QouteRules qouteBusinessRules = ProductionQouteBusinessRules.getINSTANCE();


        InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(purchaseService,quotingSystem,chargeCalculationRules,qouteBusinessRules);
        String userAuthToken = "tom@example.com";

        List<Policy> searchResults = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014);
                                                    // try also Toyota Prius, Tesla Model S, etc

        if (searchResults.isEmpty()) {
            System.out.println("No search results found");
        } else {
            Policy policy = searchResults.get(0);

            // some time may pass...
            Thread.sleep(5 * 1000);

            if (priceAcceptable(policy.premium)) {
                insuranceBroker.confirmPurchase(policy.id, userAuthToken);
            }
        }
    }

    private static boolean priceAcceptable(BigDecimal price) {
        return true;
    }
}
