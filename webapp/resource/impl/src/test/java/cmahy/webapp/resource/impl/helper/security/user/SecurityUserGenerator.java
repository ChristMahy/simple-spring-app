package cmahy.webapp.resource.impl.helper.security.user;

import cmahy.webapp.resource.impl.adapter.security.vo.UserSecurityDetails;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static cmahy.common.helper.Generator.*;
import static cmahy.common.helper.Generator.randomBoolean;

public final class SecurityUserGenerator {

    private SecurityUserGenerator() {}

    public static RequestPostProcessor generateRandomUser() {
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
                randomBoolean()
            )));
    }
}
