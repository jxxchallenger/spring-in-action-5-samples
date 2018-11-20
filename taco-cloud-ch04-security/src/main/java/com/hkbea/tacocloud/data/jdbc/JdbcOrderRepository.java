package com.hkbea.tacocloud.data.jdbc;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.hkbea.tacocloud.domain.Order;
import com.hkbea.tacocloud.domain.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private SimpleJdbcInsert orderInserter;
	
	private SimpleJdbcInsert orderTacoInserter;

	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
		super();
		this.orderInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order").usingGeneratedKeyColumns("id");
		this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order_tacos");
	}

	@Override
	//@Transactional //事务注解不应该出现在DAO层
	public Order save(Order order) {
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		for(Taco taco : order.getTacos()) {
			saveTacoToOrder(taco.getId(), orderId);
		}
		return order;
	}
	
	private long saveOrderDetails(Order order) {
		order.setPlacedAt(LocalDateTime.now());
		//String sql = "INSERT INTO taco_order (deliveryName, deliveryStreet)"
		return this.orderInserter.executeAndReturnKey(new BeanPropertySqlParameterSource(order)).longValue();
	}

	private void saveTacoToOrder(long tacoId, long orderId) {
		this.orderTacoInserter.execute(new MapSqlParameterSource().addValue("taco", tacoId).addValue("tacoOrder", orderId));
	}
}
