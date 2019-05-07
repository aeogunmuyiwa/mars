package com.mars.config;

import com.mars.aop.logging.LoggingAspect;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

	@Bean
	@Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}
}
