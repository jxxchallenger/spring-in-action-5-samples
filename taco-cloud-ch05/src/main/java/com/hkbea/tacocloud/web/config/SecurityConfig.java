package com.hkbea.tacocloud.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
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
	
	/**
	 * 角色继承关系
	 * @return
	 */
	@Bean(name = "roleHierarchy")
	public RoleHierarchy getRoleHierarchyImpl() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF > ROLE_USER > ROLE_GUEST");
		return roleHierarchy;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
			.passwordEncoder(getPasswordEncoder())
			.dataSource(dataSource);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.debug(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().antMatchers("/design", "/orders").hasRole("USER").antMatchers("/", "/**").permitAll().and().formLogin();
		http.authorizeRequests()
			.antMatchers("/design/**", "/orders/**")
			.access("hasRole('USER')")
			.antMatchers("/", "/**")
			.permitAll()
			.and()
			.formLogin()
			.loginPage("/loginForm")//如果用loginPage()方法指定了登陆页面, 而未调用loginProcessingUrl()方法指定处理登陆请求url, 则处理登陆请求url与登陆页面相同
			.loginProcessingUrl("/login")
			.and()
			.logout()
			.invalidateHttpSession(true);//默认就是true
			/*.and()
			.csrf()
			.disable()
			.headers()
			.frameOptions()
			.sameOrigin()*/
	}

}
