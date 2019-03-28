package com.hansholdings.autoconfigure.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Configuration properties for Shiro.
 *
 * @author halo
 */
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {
	/**
	 * Custom Realm 
	 */
	private String realm;
	/**
	 * URL of login
	 */
	private String loginUrl;
	/**
	 * URL of success
	 */
	private String successUrl;
	/**
	 * URL of unauthorized
	 */
	private String unauthorizedUrl;
	/**
	 * filter chain
	 */
	private Map<String, String> filterChainDefinitions;
	
	/**
	 * 
	 */
	private String ehacheConfig;
	
	public String getEhacheConfig() {
        return ehacheConfig;
    }
    public void setEhacheConfig(String ehacheConfig) {
        this.ehacheConfig = ehacheConfig;
    }
    public String getRealm() {
		return realm;
	}
	public void setRealm(String realm) {
		this.realm = realm;
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}
	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}
	public Map<String, String> getFilterChainDefinitions() {
		return filterChainDefinitions;
	}
	public void setFilterChainDefinitions(Map<String, String> filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}
	
	
}
