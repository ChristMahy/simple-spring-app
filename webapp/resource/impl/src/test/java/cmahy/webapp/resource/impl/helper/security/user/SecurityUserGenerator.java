package cmahy.webapp.resource.impl.helper.security.user;

import cmahy.webapp.resource.ui.vo.output.UserSecurityDetails;
import cmahy.webapp.user.kernel.domain.AuthProvider;
import cmahy.webapp.user.kernel.domain.id.RoleId;
import cmahy.webapp.user.kernel.domain.id.UserId;
import cmahy.webapp.user.kernel.vo.output.RoleOutputAppVo;
import cmahy.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cmahy.common.helper.Generator.*;

public final class SecurityUserGenerator {

    private static final List<String> COMMON_ROLES = List.of("Guest");

    private SecurityUserGenerator() {}

    public static RequestPostProcessor generateRandomUser() {
        return generateRandomUserWithSpecificRoles(generateCommonAppRoles());
    }

    public static RequestPostProcessor generateRandomUserWithSpecificRoles(Set<RoleOutputAppVo> roles) {
        return SecurityMockMvcRequestPostProcessors
            .user(generateRandomUserDetails(roles));
    }

    public static RequestPostProcessor generateWithSpecificUser(UserSecurityDetails userDetails) {
        return SecurityMockMvcRequestPostProcessors.user(userDetails);
    }

    public static UserSecurityDetails generateRandomUserDetails() {
        return generateRandomUserDetails(generateCommonAppRoles());
    }

    public static UserSecurityDetails generateRandomUserDetails(Set<RoleOutputAppVo> roles) {
        return new UserSecurityDetails(new UserSecurityOutputAppVo(
            new UserId(randomUUID()),
            generateAString(),
            generateAString().getBytes(),
            generateAString(),
            generateAString(),
            generateAString(),
            generateAString(),
            generateAString(),
            generateAString(),
            randomEnum(AuthProvider.class),
            randomBoolean(),
            randomBoolean(),
            randomBoolean(),
            randomBoolean(),
            roles
        ));
    }

    public static Set<RoleOutputAppVo> generateCommonAppRoles() {
        return IntStream.rangeClosed(0, COMMON_ROLES.size() - 1)
            .mapToObj(index -> new RoleOutputAppVo(new RoleId(randomUUID()), COMMON_ROLES.get(index)))
            .collect(Collectors.toSet());
    }
}
