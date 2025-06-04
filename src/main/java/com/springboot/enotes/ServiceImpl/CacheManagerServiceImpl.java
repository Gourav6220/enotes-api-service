package com.springboot.enotes.ServiceImpl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.springboot.enotes.Service.CacheManagerService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CacheManagerServiceImpl implements CacheManagerService {

	@Autowired
	private CacheManager cacheManager;
	
	public Collection<String> getCache() {
		Collection<String> cacheNames = cacheManager.getCacheNames();
		for(String cacheNamesstr:cacheNames) {
			Cache cache = cacheManager.getCache(cacheNamesstr);
			log.info("Cache Name "+cache);
		}
		return cacheNames;
		
	}

	@Override
	public Cache getCacheNames(String cacheName) {

		Cache cachename=cacheManager.getCache(cacheName);
		log.info("Cache Name= {} "+cachename);
		return cachename;
	}

	@Override
	public void removeAllCache() {
		Collection<String> cacheNames = cacheManager.getCacheNames();
		for(String cacheNamesstr:cacheNames) {
			Cache cache = cacheManager.getCache(cacheNamesstr);
			log.info("Cache Name "+cache);
			cache.clear();
		}
	}

	@Override
	public void removeAllCachebyName(List<String> cacheName) {
		for(String cacheNamesstr:cacheName) {
			Cache cache = cacheManager.getCache(cacheNamesstr);
			log.info("Cache Name "+cache);
			cache.clear();
		}
		
	}
	
	
}
