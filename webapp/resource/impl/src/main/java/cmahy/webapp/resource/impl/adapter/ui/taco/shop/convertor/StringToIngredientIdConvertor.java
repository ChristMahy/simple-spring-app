package cmahy.webapp.resource.impl.adapter.ui.taco.shop.convertor;

import cmahy.webapp.resource.ui.taco.vo.id.IngredientUiId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIngredientIdConvertor implements Converter<String, IngredientUiId> {

    @Override
    public IngredientUiId convert(String source) {
        return StringUtils.isNotBlank(source) ? new IngredientUiId(source) : null;
    }
}
