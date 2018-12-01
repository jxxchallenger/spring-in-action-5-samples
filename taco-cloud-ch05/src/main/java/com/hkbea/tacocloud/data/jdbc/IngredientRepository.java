package com.hkbea.tacocloud.data.jdbc;

import java.util.List;

import com.hkbea.tacocloud.domain.Ingredient;

public interface IngredientRepository {

	List<Ingredient> findAll();
	
	Ingredient findOne(String id);
	
	Ingredient save(Ingredient ingredient);
}
