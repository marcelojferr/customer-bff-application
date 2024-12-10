package com.javaproject.customer.bff.security.job;

import java.net.MalformedURLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.javaproject.customer.bff.security.dto.JwtTokenResponse;

@Component
@EnableScheduling
public class JwtTokenRefresh {

	private static final Logger log = LoggerFactory.getLogger(JwtTokenRefresh.class);
	private JwtTokenResponse jwtTokenResponse;
	
	@Scheduled(fixedRate = 12000000)
	void refreshToken()throws MalformedURLException{
		log.info("Refreshing token");
		
	}
	
	public String getToken() {
		return jwtTokenResponse.getAccessToken();
	}

}
