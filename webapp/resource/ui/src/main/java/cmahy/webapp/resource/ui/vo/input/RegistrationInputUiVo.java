package cmahy.webapp.resource.ui.vo.input;

import cmahy.common.validation.StringEqualityField;
import cmahy.common.validation.StringEquality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@StringEquality(message = "password.confirmation.failed")
public record RegistrationInputUiVo(
    @NotBlank(message = "registration.error.username.should-not-be-blank")
    String username,
    @StringEqualityField
    @NotBlank(message = "registration.error.password.should-not-be-blank")
    String password,
    @StringEqualityField
    @NotBlank(message = "registration.error.confirm-password.should-not-be-blank")
    String confirmPassword,
    @NotBlank(message = "registration.error.full-name.should-not-be-blank")
    String fullName,
    @NotBlank(message = "registration.error.street.should-not-be-blank")
    String street,
    @NotBlank(message = "registration.error.city.should-not-be-blank")
    String city,
    @NotBlank(message = "registration.error.state.should-not-be-blank")
    String state,
    @NotBlank(message = "registration.error.zip.should-not-be-blank")
    @Pattern(regexp = "^[0-9]+$", message = "registration.error.zip.has-to-be-only-digit")
    String zip,
    @NotBlank(message = "registration.error.phone.should-not-be-blank")
    String phone
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("username", username)
            .append("password", "...")
            .append("confirmPassword", "...")
            .append("fullName", fullName)
            .append("street", street)
            .append("city", city)
            .append("state", state)
            .append("zip", zip)
            .append("phone", phone)
            .toString();
    }
}
