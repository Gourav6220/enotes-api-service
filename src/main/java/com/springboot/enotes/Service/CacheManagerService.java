package com.springboot.enotes.Service;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.Cache;

public interface CacheManagerService {

	public Collection<String> getCache();

    public Cache getCacheNames(String cacheName);

    public void removeAllCache();
	
    public void removeAllCachebyName(List<String> cacheName);
	
}
