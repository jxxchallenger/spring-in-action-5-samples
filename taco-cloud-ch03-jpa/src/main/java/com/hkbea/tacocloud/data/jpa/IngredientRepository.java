package com.hkbea.tacocloud.data.jpa;

import org.springframework.data.repository.CrudRepository;

import com.hkbea.tacocloud.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String>{

	
}
