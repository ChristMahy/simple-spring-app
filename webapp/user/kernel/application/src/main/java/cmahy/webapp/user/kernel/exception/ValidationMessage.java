package cmahy.webapp.user.kernel.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

public record ValidationMessage(
    String i18nKey,
    Optional<Object> invalidValue
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("i18nKey", i18nKey)
            .append("invalidValue", invalidValue.orElse(null))
            .toString();
    }
}
