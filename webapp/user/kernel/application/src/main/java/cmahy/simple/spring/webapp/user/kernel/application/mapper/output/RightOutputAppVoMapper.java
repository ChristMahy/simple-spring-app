package cmahy.simple.spring.webapp.user.kernel.application.mapper.output;

import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.RightOutputAppVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class RightOutputAppVoMapper {

    public RightOutputAppVo map(Right right) {

        if (Objects.isNull(right)) {
            throw new RequiredException(Right.class);
        }

        return new RightOutputAppVo(
            Objects.nonNull(right.getId()) ? new RightId(right.getId()) : null,
            right.getName()
        );

    }
}
