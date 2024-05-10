package cmahy.webapp.resource.impl.helper.security.user;

import cmahy.webapp.resource.impl.adapter.security.vo.UserSecurityDetails;
import cmahy.webapp.resource.impl.application.user.vo.output.RoleOutputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.domain.user.id.RoleId;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;
import java.util.Set;
import java.util.stream.*;

import static cmahy.common.helper.Generator.*;

public final class SecurityUserGenerator {

    private static final List<String> COMMON_ROLES = List.of("Guest");

    private SecurityUserGenerator() {}

    public static RequestPostProcessor generateRandomUser() {
        return generateRandomUserWithSpecificRoles(generateCommonRoles());
    }

    public static RequestPostProcessor generateRandomUserWithSpecificRoles(Set<RoleOutputAppVo> roles) {
        return SecurityMockMvcRequestPostProcessors
            .user(new UserSecurityDetails(new UserSecurityOutputAppVo(
                new UserId(randomLong()),
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
            )));
    }

    public static Set<RoleOutputAppVo> generateCommonRoles() {
        return IntStream.rangeClosed(0, COMMON_ROLES.size() - 1)
            .mapToObj(index -> new RoleOutputAppVo(new RoleId(Integer.valueOf(index).longValue()), COMMON_ROLES.get(index)))
            .collect(Collectors.toSet());
    }
}
