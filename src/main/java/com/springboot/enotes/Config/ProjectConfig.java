package com.springboot.enotes.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ProjectConfig {

@Bean
public ModelMapper mapper() {
		return new ModelMapper();
	}

@Bean
public AuditorAware auditorAware() {
	return new AuditAwareConfig();
}


}
