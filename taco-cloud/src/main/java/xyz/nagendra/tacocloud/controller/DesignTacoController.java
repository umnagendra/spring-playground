package xyz.nagendra.tacocloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import xyz.nagendra.tacocloud.Ingredient;
import xyz.nagendra.tacocloud.Ingredient.Type;
import xyz.nagendra.tacocloud.Taco;
import xyz.nagendra.tacocloud.data.IngredientRepo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static xyz.nagendra.tacocloud.Ingredient.Type.*;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DesignTacoController.class);

    private final IngredientRepo ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepo ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> allIngredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(allIngredients::add);

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(allIngredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors) {
        if (errors.hasErrors()) {
            LOGGER.error("Taco design submitted with {} error(s). Taco design: {}", errors.getErrorCount(), taco);
            return "design";
        }
        // TODO save the taco designs
        LOGGER.info("Received taco design for saving: {}", taco);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(ingredient -> ingredient.getType() == type)
                .collect(Collectors.toList());
    }
}
