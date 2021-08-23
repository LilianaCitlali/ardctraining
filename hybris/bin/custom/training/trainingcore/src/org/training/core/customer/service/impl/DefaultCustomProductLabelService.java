package org.training.core.customer.service.impl;

import com.training.core.model.CustomProductLabelModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.training.core.customer.dao.CustomProductLabelDao;
import org.training.core.customer.service.CustomProductLabelService;

import java.util.Date;
import java.util.List;

public class DefaultCustomProductLabelService implements CustomProductLabelService {

    private CustomProductLabelDao customProductLabelDao;

    @Override
    public List<CustomProductLabelModel> findByCustomerAndProduct(final CustomerModel customer, final ProductModel product) {

        ServicesUtil.validateParameterNotNull(customer, "customer cannot be null");
        ServicesUtil.validateParameterNotNull(product, "product cannot be null");

        return getCustomProductLabelDao().findByCustomerAndProduct(customer,product);
    }

    public CustomProductLabelDao getCustomProductLabelDao() {
        return customProductLabelDao;
    }

    public void setCustomProductLabelDao(CustomProductLabelDao customProductLabelDao) {
        this.customProductLabelDao = customProductLabelDao;
    }

    //CronJobs
    @Override
    public List<CustomProductLabelModel> findByExpireDate(final Date now){
        //validar parameter not null?
        return getCustomProductLabelDao().findByExpireDate(now);
    }
}
