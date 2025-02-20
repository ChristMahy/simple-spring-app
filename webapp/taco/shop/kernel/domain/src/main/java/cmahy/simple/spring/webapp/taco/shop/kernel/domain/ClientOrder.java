package cmahy.simple.spring.webapp.taco.shop.kernel.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.User;
import jakarta.validation.constraints.*;

import java.util.*;

public interface ClientOrder {

    String I18N_KEY_DELIVERY_NAME_NOT_NULL = "validation.error.client-order.delivery-name.not-null";
    String I18N_KEY_DELIVERY_STREET_NOT_NULL = "validation.error.client-order.delivery-street.not-null";
    String I18N_KEY_DELIVERY_CITY_NOT_NULL = "validation.error.client-order.delivery-city.not-null";
    String I18N_KEY_DELIVERY_STATE_NOT_NULL = "validation.error.client-order.delivery-state.not-null";
    String I18N_KEY_DELIVERY_ZIP_NOT_NULL = "validation.error.client-order.delivery-zip.not-null";

    String I18N_KEY_CC_NUMBER_INVALID = "validation.error.client-order.credit-card.number.invalid";
    String I18N_KEY_CC_EXPIRATION_FORMAT_INVALID = "validation.error.client-order.credit-card.expiration.format.invalid";
    String I18N_KEY_CC_CVV_INVALID = "validation.error.client-order.credit-card.cvv.invalid";

    UUID getId();

    User getUser();

    Date getPlacedAt();

    @NotBlank(message = I18N_KEY_DELIVERY_NAME_NOT_NULL)
    String getDeliveryName();
    @NotBlank(message = I18N_KEY_DELIVERY_STREET_NOT_NULL)
    String getDeliveryStreet();
    @NotBlank(message = I18N_KEY_DELIVERY_CITY_NOT_NULL)
    String getDeliveryCity();
    @NotBlank(message = I18N_KEY_DELIVERY_STATE_NOT_NULL)
    String getDeliveryState();
    @NotBlank(message = I18N_KEY_DELIVERY_ZIP_NOT_NULL)
    String getDeliveryZip();

//    @CreditCardNumber(message = "Not a valid credit card number")
    @Digits(integer = 16, fraction = 0, message = I18N_KEY_CC_NUMBER_INVALID)
    String getCcNumber();
    @Pattern(
        regexp = "^(0[1-9]|1[0-2])/([2-9][0-9])$",
        message = I18N_KEY_CC_EXPIRATION_FORMAT_INVALID
    )
    String getCcExpiration();
    @Digits(integer = 3, fraction = 0, message = I18N_KEY_CC_CVV_INVALID)
    String getCcCVV();

    <T extends Taco> List<T> getTacos();

    void addTaco(Taco taco);
}
