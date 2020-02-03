package xyz.nagendra.tacocloud.data;

import xyz.nagendra.tacocloud.Ingredient;

public interface IngredientRepo {

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
