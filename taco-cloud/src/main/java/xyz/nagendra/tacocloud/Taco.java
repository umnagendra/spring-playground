package xyz.nagendra.tacocloud;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class Taco {

    @NotNull
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 chars")
    private String name;

    @Size(min = 1, message = "Choose at least ONE ingredient")
    private List<String> ingredients;

    public Taco() {
    }

    public Taco(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
