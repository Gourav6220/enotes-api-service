package com.springboot.enotes.Config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.springboot.enotes.Entity.User;
import com.springboot.enotes.util.CommonUtil;

public class AuditAwareConfig implements AuditorAware<Integer> {

	@Override
	public Optional<Integer> getCurrentAuditor() {
		User loggedInUser = CommonUtil.getLoggedInUser();
		
		return Optional.of(loggedInUser.getId());
	}

}
