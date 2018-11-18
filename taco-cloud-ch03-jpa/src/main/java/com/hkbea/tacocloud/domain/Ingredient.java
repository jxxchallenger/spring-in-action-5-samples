package com.hkbea.tacocloud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ingredient")
public class Ingredient {

	@Id
	private String id;
	
	private String name;
	
	@Column(name = "type", columnDefinition = "VARCHAR(10)")
	@Enumerated(EnumType.STRING)
	private Type type;

	//JPA规范要求Entity类有无参构造方法(或者不写任何构造方法，则默认隐含有个无参构造方法)
	public Ingredient() {
		super();
		
	}

	public Ingredient(String id, String name, Type type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
