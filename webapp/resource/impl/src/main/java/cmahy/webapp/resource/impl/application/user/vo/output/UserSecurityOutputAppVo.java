package cmahy.webapp.resource.impl.application.user.vo.output;

import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.domain.user.id.UserId;

public record UserSecurityOutputAppVo(
    UserId id,
    String userName,
    byte[] password,
    String fullName,
    String street,
    String city,
    String state,
    String zip,
    String phoneNumber,
    AuthProvider authProvider,
    Boolean isExpired,
    Boolean isLocked,
    Boolean isEnabled,
    Boolean isCredentialsExpired
) {
}
