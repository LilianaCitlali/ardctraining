package org.training.core.customer.dao.impl;

import com.training.core.model.CustomProductLabelModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections4.CollectionUtils;
import org.training.core.customer.dao.CustomProductLabelDao;

import java.util.*;

public class DefaultCustomProductLabelDao implements CustomProductLabelDao {

    private FlexibleSearchService flexibleSearchService;

    private static final String FIND_BY_CUSTOMER_AND_PRODUCT_QUERY =
            "SELECT {" + CustomProductLabelModel.PK + "} " +
            "FROM   {" + CustomProductLabelModel._TYPECODE + "} " +
            "WHERE  {" + CustomProductLabelModel.CUSTOMER + "} = ?customer AND " +
            "       {" + CustomProductLabelModel.PRODUCT + "} = ?product";

    private static final String FIND_BY_EXPIRE_DATE_QUERY =
            "SELECT {" + CustomProductLabelModel.PK + "} " +
            "FROM   {" + CustomProductLabelModel._TYPECODE + "} " +
            "WHERE  {" + CustomProductLabelModel.EXPIREDATE + "} < ?now";

    @Override
    public List<CustomProductLabelModel> findByCustomerAndProduct(final CustomerModel customer, final ProductModel product) {

        final Map<String, Object> params = new HashMap<>();
        params.put("customer", customer);
        params.put("product", product);

        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_BY_CUSTOMER_AND_PRODUCT_QUERY);
        query.addQueryParameters(params);

        final SearchResult<CustomProductLabelModel> result = getFlexibleSearchService().search(query);

        if(Objects.nonNull(result) && CollectionUtils.isNotEmpty(result.getResult())){
            return result.getResult();
        }

        return Collections.emptyList();
    }

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    //CronJobs
    @Override
    public List<CustomProductLabelModel> findByExpireDate(final Date now){

        final Map<String, Object> params = new HashMap<>();
        params.put("now", now);

        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_BY_EXPIRE_DATE_QUERY);
        query.addQueryParameters(params);

        final SearchResult<CustomProductLabelModel> result = getFlexibleSearchService().search(query);

        if(Objects.nonNull(result) && CollectionUtils.isNotEmpty(result.getResult())){
            return result.getResult();
        }

        return Collections.emptyList();
    }

}
