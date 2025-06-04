package com.springboot.enotes.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Endpoints.CacheEndpoint;
import com.springboot.enotes.Service.CacheManagerService;
import com.springboot.enotes.util.CommonUtil;

@RestController
public class CacheController implements CacheEndpoint {

	@Autowired
	private CacheManagerService cacheManagerService;
	@Override
	public ResponseEntity<?> getallCache() {
		
		Collection<String> cache = cacheManagerService.getCache();
		
		return CommonUtil.createBuildResponse(cache, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getCache(String cache_name) {
		Cache cacheNames = cacheManagerService.getCacheNames(cache_name);
		
		return CommonUtil.createBuildResponse(cacheNames, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> removeAllcache() {
	
	cacheManagerService.removeAllCache();
	return CommonUtil.createBuildResponseMessage("Successfully Remove All Cahces", HttpStatus.OK);

	
	}

}
