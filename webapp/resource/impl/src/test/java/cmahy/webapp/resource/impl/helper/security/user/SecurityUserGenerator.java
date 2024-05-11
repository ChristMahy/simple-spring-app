package cmahy.webapp.resource.impl.helper.security.user;

import cmahy.webapp.resource.impl.application.user.vo.output.RoleOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.domain.user.id.RoleId;
import cmahy.webapp.resource.user.api.security.vo.output.UserSecurityDetails;
import cmahy.webapp.resource.user.api.vo.id.RoleApiId;
import cmahy.webapp.resource.user.api.vo.id.UserApiId;
import cmahy.webapp.resource.user.api.vo.output.RoleOutputApiVo;
import cmahy.webapp.resource.user.api.vo.output.UserSecurityOutputApiVo;
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
        return generateRandomUserWithSpecificRoles(generateCommonApiRoles());
    }

    public static RequestPostProcessor generateRandomUserWithSpecificRoles(Set<RoleOutputApiVo> roles) {
        return SecurityMockMvcRequestPostProcessors
            .user(generateRandomUserDetails(roles));
    }

    public static RequestPostProcessor generateWithSpecificUser(UserSecurityDetails userDetails) {
        return SecurityMockMvcRequestPostProcessors.user(userDetails);
    }

    public static UserSecurityDetails generateRandomUserDetails() {
        return generateRandomUserDetails(generateCommonApiRoles());
    }

    public static UserSecurityDetails generateRandomUserDetails(Set<RoleOutputApiVo> roles) {
        return new UserSecurityDetails(new UserSecurityOutputApiVo(
            new UserApiId(randomLong()),
            generateAString(),
            generateAString().getBytes(),
            generateAString(),
            generateAString(),
            generateAString(),
            generateAString(),
            generateAString(),
            generateAString(),
            randomEnum(AuthProvider.class).name(),
            randomBoolean(),
            randomBoolean(),
            randomBoolean(),
            randomBoolean(),
            roles
        ));
    }

    public static Set<RoleOutputApiVo> generateCommonApiRoles() {
        return IntStream.rangeClosed(0, COMMON_ROLES.size() - 1)
            .mapToObj(index -> new RoleOutputApiVo(new RoleApiId(Integer.valueOf(index).longValue()), COMMON_ROLES.get(index)))
            .collect(Collectors.toSet());
    }

    public static Set<RoleOutputAppVo> generateCommonAppRoles() {
        return IntStream.rangeClosed(0, COMMON_ROLES.size() - 1)
            .mapToObj(index -> new RoleOutputAppVo(new RoleId(Integer.valueOf(index).longValue()), COMMON_ROLES.get(index)))
            .collect(Collectors.toSet());
    }
}
