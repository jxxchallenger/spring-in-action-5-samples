package com.hkbea.tacocloud.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hkbea.tacocloud.data.jpa.IngredientRepository;
import com.hkbea.tacocloud.data.jpa.TacoRepository;
import com.hkbea.tacocloud.domain.Ingredient;
import com.hkbea.tacocloud.domain.Order;
import com.hkbea.tacocloud.domain.Taco;
import com.hkbea.tacocloud.domain.Type;

@Controller
@RequestMapping(value = {"/design"})
@SessionAttributes("order")
public class DesignTacoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DesignTacoController.class);
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private TacoRepository tacoRepository;
	
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		LOGGER.info("add ingredient to model");
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		this.ingredientRepository.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Type.values();
		
		for(Type type: types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		
	}
	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@GetMapping
	public String showDesignForm(Model model) {
		
		LOGGER.info("start deal /design request");
		
		model.addAttribute("design", new Taco());
		LOGGER.info("end deal /design request");
		return "design";
	}
	
	@PostMapping
	public String processDesign(@Valid @ModelAttribute(value = "design") Taco design, Errors errors, @ModelAttribute(name = "order") Order order) {
		
		if(errors.hasErrors()) {
			return "design";
		}
		this.tacoRepository.save(design);
		order.addDesign(design);
		
		return "redirect:/orders/current";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		LOGGER.info("filter ingredients by type");
		
		List<Ingredient> result = new ArrayList<Ingredient>();
		for(Ingredient ingredient:ingredients) {
			if(ingredient.getType().equals(type)) {
				result.add(ingredient);
			}
		}
		
		return result;
	}
}
