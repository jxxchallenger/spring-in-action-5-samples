package com.hkbea.tacocloud.data.jdbc;

import com.hkbea.tacocloud.domain.Order;

public interface OrderRepository {

	Order save(Order order);
}
