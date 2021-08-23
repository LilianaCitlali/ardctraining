package org.training.facades.populators;

import com.training.core.model.CustomProductLabelModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.training.facades.customer.data.CustomLabelProductData;

public class CustomProductLabelPopulator implements Populator<CustomProductLabelModel, CustomLabelProductData> {

    @Override
    public void populate(final CustomProductLabelModel source, final CustomLabelProductData target) throws ConversionException {
        target.setProduct(source.getProduct().getCode());
        target.setCustomer(source.getCustomer().getUid());
        target.setLabel(source.getLabel());
        target.setExpireDate(source.getExpireDate());
    }
}
