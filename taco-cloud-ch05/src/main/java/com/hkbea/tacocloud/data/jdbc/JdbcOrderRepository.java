package com.hkbea.tacocloud.data.jdbc;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.hkbea.tacocloud.domain.Order;
import com.hkbea.tacocloud.domain.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private SimpleJdbcInsert orderInserter;
	
	private SimpleJdbcInsert orderTacoInserter;
	
	private SimpleJdbcInsert OrderUserInserter;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
		super();
		this.orderInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order").usingGeneratedKeyColumns("id");
		this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order_tacos");
		this.OrderUserInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_orders");
	}

	@Override
	//@Transactional //事务注解不应该出现在DAO层
	public Order save(Order order) {
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		for(Taco taco : order.getTacos()) {
			saveTacoToOrder(taco.getId(), orderId);
		}
		saveOrderToUser(orderId, order.getUser().getUsername());
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
	
	private void saveOrderToUser(long orderId, String username) {
		this.OrderUserInserter.execute(new MapSqlParameterSource().addValue("order_id", orderId).addValue("username", username));
	}

	@Override
	public List<Order> findByUser(String username, int start, int end) {
		String sql = "SELECT o.id, o.deliveryName, o.deliveryStreet, o.deliveryCity, o.deliveryState, o.deliveryZip, o.ccNumber, o.ccExpiration, o.ccCVV, o.placedAt FROM taco_order o JOIN user_orders u ON o.id = u.order_id WHERE u.username = :username LIMIT :start, :end";
		return this.namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource().addValue("username", username).addValue("start", start).addValue("end", end), new BeanPropertyRowMapper<Order>(Order.class));
	}
}
