package xyz.nagendra.tacocloud;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

public class Taco {

    private String name;
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
