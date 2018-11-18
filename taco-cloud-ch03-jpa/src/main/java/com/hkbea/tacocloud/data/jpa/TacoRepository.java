package com.hkbea.tacocloud.data.jpa;

import org.springframework.data.repository.CrudRepository;

import com.hkbea.tacocloud.domain.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
	
}
