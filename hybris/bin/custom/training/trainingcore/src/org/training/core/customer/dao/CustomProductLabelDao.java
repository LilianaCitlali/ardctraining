package org.training.core.customer.dao;

import com.training.core.model.CustomProductLabelModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.Date;
import java.util.List;

public interface CustomProductLabelDao {

    /**
     * find by customer and product
     * @param customer
     * @param product
     * @return
     */

    List<CustomProductLabelModel> findByCustomerAndProduct(CustomerModel customer, ProductModel product);

    List<CustomProductLabelModel> findByExpireDate(final Date now);

    }
