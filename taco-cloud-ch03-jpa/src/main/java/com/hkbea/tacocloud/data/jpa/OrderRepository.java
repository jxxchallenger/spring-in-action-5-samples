package com.hkbea.tacocloud.data.jpa;

import org.springframework.data.repository.CrudRepository;

import com.hkbea.tacocloud.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
