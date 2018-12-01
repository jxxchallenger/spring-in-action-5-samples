package com.hkbea.tacocloud.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.security.core.userdetails.User;

public class Order {
	
	private Long id;
	
	private LocalDateTime placedAt;
	
	@NotBlank(message = "Name is required")
	private String deliveryName;
	
	@NotBlank(message = "Street is required")
	private String deliveryStreet;
	
	@NotBlank(message = "City is required")
	private String deliveryCity;
	
	@NotBlank(message = "State is required")
	private String deliveryState;
	
	@NotBlank(message = "Zip code is required")
	private String deliveryZip;
	
	@CreditCardNumber(message = "Not a valid credit card number")
	private String ccNumber;
	
	@Pattern(regexp = "^(0[1-9]|1[0-2])(/)([1-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration;
	
	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	private String ccCVV;
	
	private List<Taco> tacos = new ArrayList<Taco>();

	private User user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getPlacedAt() {
		return placedAt;
	}

	public void setPlacedAt(LocalDateTime placedAt) {
		this.placedAt = placedAt;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryStreet() {
		return deliveryStreet;
	}

	public void setDeliveryStreet(String deliveryStreet) {
		this.deliveryStreet = deliveryStreet;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}

	public String getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}

	public String getDeliveryZip() {
		return deliveryZip;
	}

	public void setDeliveryZip(String deliveryZip) {
		this.deliveryZip = deliveryZip;
	}

	public void setTacos(List<Taco> tacos) {
		this.tacos = tacos;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExpiration() {
		return ccExpiration;
	}

	public void setCcExpiration(String ccExpiration) {
		this.ccExpiration = ccExpiration;
	}

	public String getCcCVV() {
		return ccCVV;
	}

	public void setCcCVV(String ccCVV) {
		this.ccCVV = ccCVV;
	}

	public List<Taco> getTacos() {
		return tacos;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", placedAt=" + placedAt + ", deliveryName=" + deliveryName + ", deliveryStreet="
				+ deliveryStreet + ", deliveryCity=" + deliveryCity + ", deliveryState=" + deliveryState
				+ ", deliveryZip=" + deliveryZip + ", ccNumber=" + ccNumber + ", ccExpiration=" + ccExpiration
				+ ", ccCVV=" + ccCVV + ", tacos=" + tacos + "]";
	}

	public void addDesign(Taco taco) {
		this.tacos.add(taco);
		
	}
}
