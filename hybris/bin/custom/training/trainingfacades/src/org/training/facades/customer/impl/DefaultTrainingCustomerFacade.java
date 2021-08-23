package org.training.facades.customer.impl;

import com.training.core.model.CustomProductLabelModel;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;
import org.training.core.customer.service.CustomProductLabelService;
import org.training.facades.customer.TrainingCustomerFacade;
import org.training.facades.customer.data.CustomLabelProductData;
import java.util.ArrayList;
import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

public class DefaultTrainingCustomerFacade extends DefaultCustomerFacade implements TrainingCustomerFacade {

    private UserService userService;
    private ProductService productService;
    private CustomProductLabelService customProductLabelService;
    private Populator<CustomProductLabelModel, CustomLabelProductData> customProductLabelPopulator;
    private Converter<CustomProductLabelModel, CustomLabelProductData> customProductLabelConverter;


    //segundo Custom2Product
    @Override
    public List<CustomLabelProductData> findProductCustomLabelsByCurrentUser(final String productCode) {
        final UserModel currentUser = getUserService().getCurrentUser();
        final CustomerModel customer = (CustomerModel) currentUser;
        final ProductModel product = getProductService().getProductForCode(productCode);

        final List<CustomProductLabelModel> productLabelModels = getCustomProductLabelService().findByCustomerAndProduct(customer, product);

        /*if(CollectionUtils.isNotEmpty(productLabelModels)){
            final List<CustomLabelProductData> result = new ArrayList<>();

            productLabelModels.forEach((CustomProductLabelModel item) ->{
                final CustomLabelProductData target = new CustomLabelProductData();
                getCustomProductLabelPopulator().populate(item, target);
                result.add(target);
            });
        }*/
        //return result
        return getCustomProductLabelConverter().convertAll(productLabelModels);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public CustomProductLabelService getCustomProductLabelService() {
        return customProductLabelService;
    }

    public void setCustomProductLabelService(CustomProductLabelService customProductLabelService) {
        this.customProductLabelService = customProductLabelService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public Populator<CustomProductLabelModel, CustomLabelProductData> getCustomProductLabelPopulator() {
        return customProductLabelPopulator;
    }

    public void setCustomProductLabelPopulator(Populator<CustomProductLabelModel, CustomLabelProductData> customProductLabelPopulator) {
        this.customProductLabelPopulator = customProductLabelPopulator;
    }

    public Converter<CustomProductLabelModel, CustomLabelProductData> getCustomProductLabelConverter() {
        return customProductLabelConverter;
    }

    public void setCustomProductLabelConverter(Converter<CustomProductLabelModel, CustomLabelProductData> customProductLabelConverter) {
        this.customProductLabelConverter = customProductLabelConverter;
    }

    //COMLILI primero: new label phoneNumber
    @Override
    public void register(final RegisterData registerData) throws DuplicateUidException
    {
        validateParameterNotNullStandardMessage("registerData", registerData);
        Assert.hasText(registerData.getFirstName(), "The field [FirstName] cannot be empty");
        Assert.hasText(registerData.getLastName(), "The field [LastName] cannot be empty");
        Assert.hasText(registerData.getLogin(), "The field [Login] cannot be empty");

        final CustomerModel newCustomer = getModelService().create(CustomerModel.class);
        setCommonPropertiesForRegister(registerData, newCustomer);
        getCustomerAccountService().register(newCustomer, registerData.getPassword());
    }

    @Override
    protected void setCommonPropertiesForRegister(final RegisterData registerData, final CustomerModel customerModel)
    {
        customerModel.setName(getCustomerNameStrategy().getName(registerData.getFirstName(), registerData.getLastName()));
        setTitleForRegister(registerData, customerModel);
        setUidForRegister(registerData, customerModel);
        customerModel.setPhoneNumber(registerData.getPhoneNumber());
        customerModel.setSessionLanguage(getCommonI18NService().getCurrentLanguage());
        customerModel.setSessionCurrency(getCommonI18NService().getCurrentCurrency());
    }

}
