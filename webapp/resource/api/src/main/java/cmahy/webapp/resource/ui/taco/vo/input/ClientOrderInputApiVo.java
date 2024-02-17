package cmahy.webapp.resource.ui.taco.vo.input;

import jakarta.validation.constraints.*;

import java.util.List;

public record ClientOrderInputApiVo(
    @NotBlank(message = "Delivery name is required")
    String deliveryName,
    @NotBlank(message = "Street is required")
    String deliveryStreet,
    @NotBlank(message = "City is required")
    String deliveryCity,
    @NotBlank(message = "State is required")
    String deliveryState,
    @NotBlank(message = "Zip code is required")
    String deliveryZip,
//    @CreditCardNumber(message = "Not a valid credit card number")
    @Digits(integer = 16, fraction = 0, message = "Not a valid credit card number")
    String ccNumber,
    @Pattern(
        regexp = "^(0[1-9]|1[0-2])/([2-9][0-9])$",
        message = "Must be formatted MM/YY"
    )
    String ccExpiration,
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    String ccCVV,
    List<TacoInputApiVo> tacos
) {
}
