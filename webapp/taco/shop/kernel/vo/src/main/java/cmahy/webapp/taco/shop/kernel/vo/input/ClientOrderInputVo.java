package cmahy.webapp.taco.shop.kernel.vo.input;

import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.List;

public record ClientOrderInputVo(
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
    List<TacoInputVo> tacos
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("deliveryName", deliveryName())
            .append("deliveryStreet", deliveryStreet())
            .append("deliveryCity", deliveryCity())
            .append("deliveryState", deliveryState())
            .append("deliveryZip", deliveryZip())
            .append("ccNumber", ccNumber())
            .append("ccExpiration", ccExpiration())
            .append("ccCVV", ccCVV())
            .append("tacos", tacos() == null ? Collections.emptyList() : tacos().stream().map(TacoInputVo::name).toList())
            .build();
    }
}
