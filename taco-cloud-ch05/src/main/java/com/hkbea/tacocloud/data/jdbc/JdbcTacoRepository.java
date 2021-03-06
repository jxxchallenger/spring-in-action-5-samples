package com.hkbea.tacocloud.data.jdbc;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.hkbea.tacocloud.domain.Ingredient;
import com.hkbea.tacocloud.domain.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	//@Transactional(propagation = Propagation.REQUIRED) //事务注解不应该出现在DAO层
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		for(Ingredient ingredient:taco.getIngredients()) {
			saveIngredientToTaco(ingredient.getId(), taco.getId());
		}
		
		return taco;
	}

	private Long saveTacoInfo(Taco taco) {
		taco.setCreateAt(LocalDateTime.now());
		String sql = "INSERT INTO taco(name, createAt) VALUES(:name, :createAt)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(taco), keyHolder);
		
		return keyHolder.getKey().longValue();
	}
	
	private void saveIngredientToTaco(String ingredient, long tacoId) {
		String sql = "INSERT INTO taco_ingredients (taco, ingredient) VALUES (:tacoId, :ingredientId)";
		this.namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource().addValue("tacoId", tacoId).addValue("ingredientId", ingredient));
	}
}
