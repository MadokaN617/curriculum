package com.example.security.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security.springsecurity.account.AccountService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccountService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/login", "/login-error").permitAll()
		.antMatchers("/**").hasRole("USER")
		.and()
		.formLogin()
		.loginPage("/login").failureUrl("/login-error");
	}


	//変更点 ロード時に、「admin」ユーザを登録する。
	/*~~(Migrate manually based on https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)~~>*/@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userService)
		.passwordEncoder(passwordEncoder());

		if (userService.findAllList().isEmpty()) {
			userService.registerAdmin("admin", "secret", "admin@localhost");
			userService.registerManager("manager", "secret", "manager@localhost");
			userService.registerUser("user", "secret", "user@localhost");
		}
	}
	//変更点 PasswordEncoder(BCryptPasswordEncoder)メソッド
	@Bean
	public PasswordEncoder passwordEncoder() {
		//
		return new BCryptPasswordEncoder();
	}

}