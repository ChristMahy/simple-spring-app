package cmahy.webapp.resource.impl.domain.taco;

import cmahy.webapp.resource.impl.application.taco.shop.exception.IngredientTypeNotFoundException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Ingredient {

    @Id
    private String id;
    private String name;
    private Type type;

    public Ingredient() {}

    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        WRAP,
        PROTEIN,
        VEGGIES,
        CHEESE,
        SAUCE;

        static Type fromString(String value) {
            Set<Type> types = Arrays.stream(Type.values())
                .filter(t -> StringUtils.equalsIgnoreCase(t.name(), value))
                .collect(Collectors.toSet());

            if (types.size() != 1) {
                throw new IngredientTypeNotFoundException(value);
            }

            return types.stream().findFirst().get();
        }
    }
}
