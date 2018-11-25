package com.hkbea.tacocloud.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	/*@Bean
	public JdbcUserDetailsManager getUserDetailsManager() {
		return new JdbcUserDetailsManager(dataSource);
	}*/
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
			.passwordEncoder(getPasswordEncoder())
			.dataSource(dataSource);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().antMatchers("/design", "/orders").hasRole("USER").antMatchers("/", "/**").permitAll().and().formLogin();
		http.authorizeRequests()
			.antMatchers("/design", "/orders")
			.access("hasRole('USER')")
			.antMatchers("/", "/**")
			.permitAll()
			.and()
			.formLogin()
			.loginPage("/loginForm")
			.loginProcessingUrl("/login");//如果用loginPage()方法指定了登陆页面, 而未调用loginProcessingUrl()方法指定处理登陆请求url, 则处理登陆请求url与登陆页面相同
	}

}
