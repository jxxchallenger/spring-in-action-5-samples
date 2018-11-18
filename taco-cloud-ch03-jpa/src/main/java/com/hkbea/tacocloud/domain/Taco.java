package com.hkbea.tacocloud.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "taco")
public class Taco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime createAt;
	
	@NotNull
	@Size(min = 5, message="Name must be at least 5 characters long")
	private String name;
	
	@NotNull(message = "You must choose at least 1 ingredient")
	@Size(min = 1, message="You must choose at least 1 ingredient")
	@ManyToMany(targetEntity = Ingredient.class)
	private List<Ingredient> ingredients;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@PrePersist
	void createAt() {
		this.createAt = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		return "Taco [id=" + id + ", createAt=" + createAt + ", name=" + name + ", ingredients=" + ingredients + "]";
	}

	
}
