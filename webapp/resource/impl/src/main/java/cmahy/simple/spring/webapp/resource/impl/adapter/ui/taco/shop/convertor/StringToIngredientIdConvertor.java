package cmahy.simple.spring.webapp.resource.impl.adapter.ui.taco.shop.convertor;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToIngredientIdConvertor implements Converter<String, IngredientId> {

    @Override
    public IngredientId convert(String source) {
        return StringUtils.isNotBlank(source) ? new IngredientId(UUID.fromString(source)) : null;
    }
}
