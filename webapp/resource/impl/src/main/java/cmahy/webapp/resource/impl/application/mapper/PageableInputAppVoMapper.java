package cmahy.webapp.resource.impl.application.mapper;

import cmahy.common.entity.page.DefaultEntityPageableImpl;
import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class PageableInputAppVoMapper {

    public EntityPageable map(PageableInputAppVo pageableInputAppVo) {
        if (Objects.isNull(pageableInputAppVo)) {
            throw new NullException(PageableInputAppVo.class);
        }

        return new DefaultEntityPageableImpl(
            pageableInputAppVo.pageNumber(),
            pageableInputAppVo.pageSize()
        );
    }
}
