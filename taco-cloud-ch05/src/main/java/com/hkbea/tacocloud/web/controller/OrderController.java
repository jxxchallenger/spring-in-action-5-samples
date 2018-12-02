package com.hkbea.tacocloud.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.hkbea.tacocloud.data.jdbc.OrderRepository;
import com.hkbea.tacocloud.domain.Order;
import com.hkbea.tacocloud.web.OrderProps;

@Controller
@RequestMapping(value = {"/orders"})
@SessionAttributes("order")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProps orderProps;

	@GetMapping(value = {"/current"})
	public String orderForm(Model model) {
		//order设置为session
		//model.addAttribute("order", new Order());
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid @ModelAttribute Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
		
		if(errors.hasErrors()) {
			return "orderForm";
		}
		LOGGER.info("Order submitted: " + order);
		order.setUser(user);
		orderRepository.save(order);
		sessionStatus.setComplete();
		
		
		return "redirect:/";
	}
	
	@GetMapping
	public String ordersForUser(@AuthenticationPrincipal User user, Model model) { 
		model.addAttribute("orders", this.orderRepository.findByUser(user.getUsername(), 0, orderProps.getPageSize()));
		
		return "orderList";
	}
}
