package org.jinstagram.auth.model;

import java.net.Proxy;

public class OAuthConfig {
	private final String apiKey;

	private final String apiSecret;

	private final String callback;

	private final String display;

	private final String scope;
	
	private final String state;

	private Proxy requestProxy;

	public OAuthConfig(String key, String secret) {
		this(key, secret, null, null, null);
	}

	public OAuthConfig(String key, String secret, String callback, String scope, String state) {
		this(key, secret, callback, scope, null, state);
	}

	public OAuthConfig(String key, String secret, String callback, String scope, String display, String state) {
		this.apiKey = key;
		this.apiSecret = secret;
		this.callback = (callback != null) ? callback : OAuthConstants.OUT_OF_BAND;
		this.scope = scope;
		this.display = display;
		this.state = state;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public String getCallback() {
		return callback;
	}

	public String getScope() {
		return scope;
	}

	public boolean hasScope() {
		return scope != null;
	}
	
	public boolean hasState() {
		return state != null;
	}

	public String getDisplay() {
		return display;
	}	
	
	public String getState() {
		return state;
	}

	/**
	 * @param requestProxy the proxy to set
	 */
	public void setRequestProxy(Proxy requestProxy) {
		this.requestProxy = requestProxy;
	}

	public Proxy getRequestProxy() {
		return requestProxy;
	}
}
