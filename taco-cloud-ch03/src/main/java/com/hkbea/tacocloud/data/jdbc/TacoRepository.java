package com.hkbea.tacocloud.data.jdbc;

import com.hkbea.tacocloud.domain.Taco;

public interface TacoRepository {

	Taco save(Taco taco);
	
}
