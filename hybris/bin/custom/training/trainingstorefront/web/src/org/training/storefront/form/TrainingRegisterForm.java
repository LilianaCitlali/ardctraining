package org.training.storefront.form;

import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;

public class TrainingRegisterForm extends RegisterForm {

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
