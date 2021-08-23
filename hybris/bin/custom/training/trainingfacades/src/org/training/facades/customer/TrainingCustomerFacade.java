package org.training.facades.customer;

import de.hybris.platform.commercefacades.customer.CustomerFacade;
import org.training.facades.customer.data.CustomLabelProductData;
import java.util.List;

public interface TrainingCustomerFacade extends CustomerFacade {

    List<CustomLabelProductData> findProductCustomLabelsByCurrentUser(String productCode);
}
