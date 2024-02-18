package cmahy.webapp.resource.ui.taco.vo.input;

import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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

    public ClientOrderInputApiVo addATaco(TacoInputApiVo taco) {
        return new ClientOrderInputApiVo(
            this.deliveryName(),
            this.deliveryStreet(),
            this.deliveryCity(),
            this.deliveryState(),
            this.deliveryZip(),
            this.ccNumber(),
            this.ccExpiration(),
            this.ccCVV(),
            Stream.concat(this.tacos().stream(), Stream.of(taco)).toList()
        );
    }

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
            .append("tacos", tacos() == null ? Collections.emptyList() : tacos().stream().map(TacoInputApiVo::name).toList())
            .build();
    }

    public static ClientOrderInputApiVo createEmpty() {
        return new ClientOrderInputApiVo("", "", "", "", "", "", "", "", Collections.emptyList());
    }
}
