package cmahy.webapp.resource.ui.taco.vo.output;

import java.util.Collection;

public record ClientOrderPageOutputUiVo(
    Collection<ClientOrderOutputUiVo> content,
    Long totalElements
) /*implements cmahy.webapp.resource.impl.application.vo.output.PageOutputVo*/ {
}
