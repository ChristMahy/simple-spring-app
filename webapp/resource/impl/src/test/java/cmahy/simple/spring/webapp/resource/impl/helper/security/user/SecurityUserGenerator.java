package cmahy.simple.spring.webapp.resource.impl.helper.security.user;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.local.vo.input.TacoResourceUserDetailsInputVo;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import cmahy.simple.spring.webapp.user.kernel.vo.output.RoleOutputAppVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cmahy.simple.spring.common.helper.Generator.*;

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

    public static RequestPostProcessor generateWithSpecificUser(TacoResourceUserDetailsInputVo userDetails) {
        return SecurityMockMvcRequestPostProcessors.user(userDetails);
    }

    public static TacoResourceUserDetailsInputVo generateRandomUserDetails() {
        return generateRandomUserDetails(generateCommonAppRoles());
    }

    public static TacoResourceUserDetailsInputVo generateRandomUserDetails(Set<RoleOutputAppVo> roles) {
        return new TacoResourceUserDetailsInputVo(new UserSecurityOutputAppVo(
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
            // TODO: Add rights...
            .mapToObj(index -> new RoleOutputAppVo(new RoleId(randomUUID()), COMMON_ROLES.get(index), Set.of()))
            .collect(Collectors.toSet());
    }
}
