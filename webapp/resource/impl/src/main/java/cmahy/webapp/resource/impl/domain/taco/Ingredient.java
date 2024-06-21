package cmahy.webapp.resource.impl.domain.taco;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Ingredient {

    public static final String I18N_KEY_NAME_NOT_NULL = "validation.error.ingredient.name.not-null";
    public static final String I18N_KEY_NAME_NOT_BLANK = "validation.error.ingredient.name.not-blank";

    public static final String I18N_KEY_TYPE_NOT_NULL = "validation.error.ingredient.type.not-null";

    @Id
    private String id;

    @NotNull(message = I18N_KEY_NAME_NOT_NULL)
    @NotBlank(message = I18N_KEY_NAME_NOT_BLANK)
    private String name;

    @NotNull(message = I18N_KEY_TYPE_NOT_NULL)
    private IngredientType type;

    public Ingredient() {}

    public Ingredient(String id, String name, IngredientType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Ingredient setId(String id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return name;
    }

    public Ingredient setName(String name) {
        this.name = name;

        return this;
    }

    public IngredientType getType() {
        return type;
    }

    public Ingredient setType(IngredientType type) {
        this.type = type;

        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("type", getType())
            .toString();
    }
}
