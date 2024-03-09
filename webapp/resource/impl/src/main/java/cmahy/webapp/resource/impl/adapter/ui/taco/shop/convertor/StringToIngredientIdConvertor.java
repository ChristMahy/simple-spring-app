package cmahy.webapp.resource.impl.adapter.ui.taco.shop.convertor;

import cmahy.webapp.resource.ui.taco.vo.id.IngredientApiId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIngredientIdConvertor implements Converter<String, IngredientApiId> {

    @Override
    public IngredientApiId convert(String source) {
        return StringUtils.isNotBlank(source) ? new IngredientApiId(source) : null;
    }
}
