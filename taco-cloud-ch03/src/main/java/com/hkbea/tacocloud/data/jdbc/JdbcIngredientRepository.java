package com.hkbea.tacocloud.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.hkbea.tacocloud.domain.Ingredient;
import com.hkbea.tacocloud.domain.Type;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<Ingredient> findAll() {
		
		String sql = "SELECT id, name, type FROM ingredient";
		
		return this.namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<Ingredient>(Ingredient.class));
	}

	@Override
	public Ingredient findOne(String id) {
		
		String sql = "SELECT id, name, type FROM ingredient WHERE id=:id";
		SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
		return this.namedParameterJdbcTemplate.queryForObject(sql, parameterSource, new IngredientRowMapper());
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		
		String sql = "INSERT INTO ingredient (id, name, type) VALUES(:id, :name, :type)";
		this.namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(ingredient));
		return ingredient;
	}

	private static class IngredientRowMapper implements RowMapper<Ingredient> {

		@Override
		public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ingredient ingredient = new Ingredient();
			ingredient.setId(rs.getString("id"));
			ingredient.setName(rs.getString("name"));
			ingredient.setType(Type.valueOf(rs.getString("type")));
			return ingredient;
		}
		
	}
	
}
