package cmahy.simple.spring.webapp.authorization.application.mapper.output;

import cmahy.simple.spring.webapp.authorization.application.vo.output.RightOutputAppVo;
import cmahy.simple.spring.webapp.authorization.domain.Right;
import cmahy.simple.spring.webapp.authorization.domain.id.RightId;
import jakarta.inject.Named;

@Named
public class RightOutputAppMapper {

    public RightOutputAppVo map(Right right) {
        return new RightOutputAppVo(
            new RightId(right.getId()),
            right.getName()
        );
    }
}
