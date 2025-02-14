package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration //Marks this class as a configuration class.
@EnableWebSecurity //Enables Spring Security
public class MyConfig  {
    @Bean
    //Loads user details from the database.
	public UserDetailService getUserDetailService() {
	return new UserDetailService();
    }
    @Bean
    // encrypts password
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    @Bean
    // Authenticates users using UserDetailsService and BCryptPasswordEncoder.
    public DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider daoAuthentationProvider =new DaoAuthenticationProvider();
    	
    	daoAuthentationProvider.setUserDetailsService(this.getUserDetailService());
    	daoAuthentationProvider.setPasswordEncoder(passwordEncoder());
    	
    	return daoAuthentationProvider;
    }
    
    @Bean
    // Manages authentication.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
	// Defines security rules.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http
            .authorizeHttpRequests(auth->auth
        		  .requestMatchers("/admin/**").hasRole("ADMIN")
        		  .requestMatchers("/user/**").hasRole("USER")
        		  .requestMatchers("/**").permitAll()   
        		  )    		  
        		
        	 .formLogin(form->form
        			 
        			  .permitAll()
        			  )
        	
        	.logout(logout->logout
        			.logoutUrl("/logout")
        			.logoutSuccessUrl("/")
        			)
        	
        	.csrf(csrf->csrf.disable()
        			 
        	  );		   
               
        		
    	
    	return http.build();
     }
}
