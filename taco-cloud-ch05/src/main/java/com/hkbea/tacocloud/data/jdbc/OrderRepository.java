package com.hkbea.tacocloud.data.jdbc;

import java.util.List;

import com.hkbea.tacocloud.domain.Order;

public interface OrderRepository {

	Order save(Order order);
	
	List<Order> findByUser(String username, int start, int end);
}
