package org.jinstagram.auth;

import java.net.Proxy;

import org.jinstagram.auth.model.OAuthConfig;
import org.jinstagram.auth.model.OAuthConstants;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.auth.oauth.OAuthService;
import org.jinstagram.utils.Preconditions;

public class InstagramAuthService {
	private String apiKey;

	private String apiSecret;

	private String callback;

	private String display;

	private String scope;
	
	private String state;

	private Proxy requestProxy;

	/**
	 * Default constructor
	 */
	public InstagramAuthService() {
		this.callback = OAuthConstants.OUT_OF_BAND;
	}

	/**
	 * Adds an OAuth callback url
	 * 
	 * @param callback callback url. Must be a valid url or 'oob' for out of
	 * band OAuth
	 * @return the {@link InstagramAuthService} instance for method chaining
	 */
	public InstagramAuthService callback(String callback) {
		Preconditions.checkValidOAuthCallback(callback, "Callback must be a valid URL or 'oob'");

		this.callback = callback;

		return this;
	}

	/**
	 * Configures the api key
	 * 
	 * @param apiKey The api key for your application
	 * @return the {@link InstagramAuthService} instance for method chaining
	 */
	public InstagramAuthService apiKey(String apiKey) {
		Preconditions.checkEmptyString(apiKey, "Invalid Api key");

		this.apiKey = apiKey;

		return this;
	}

	/**
	 * Configures the api secret
	 * 
	 * @param apiSecret The api secret for your application
	 * @return the {@link InstagramAuthService} instance for method chaining
	 */
	public InstagramAuthService apiSecret(String apiSecret) {
		Preconditions.checkEmptyString(apiSecret, "Invalid Api secret");

		this.apiSecret = apiSecret;

		return this;
	}

	/**
	 * Configures the OAuth scope. This is only necessary in some APIs (like
	 * Google's).
	 * 
	 * @param scope The OAuth scope
	 * @return the {@link InstagramAuthService} instance for method chaining
	 */
	public InstagramAuthService scope(String scope) {
		Preconditions.checkEmptyString(scope, "Invalid OAuth scope");

		this.scope = scope;

		return this;
	}

	/**
	 * Configures the Display parameter ; if you want a mobile-optimized
	 * authorization screen
	 * 
	 * @param display The Display parameter for mobile devices
	 * @return the {@link InstagramAuthService} instance for method chaining
	 */
	public InstagramAuthService display(String display) {
		Preconditions.checkEmptyString(display, "Invalid Display scope");

		this.display = display;

		return this;
	}

	/**
	 * Configures the state parameter 
	 * 
	 * @param state state parameter
	 * @return the {@link InstagramAuthService} instance for method chaining
	 */
	public InstagramAuthService state(String state) {
		Preconditions.checkEmptyString(state, "Invalid State value");

		this.state = state;

		return this;
	}
	
	/**
	 * Configures the Display parameter ; if you want a mobile-optimized
	 * authorization screen
	 *
	 * @param requestProxy The {@link Proxy} object to be used by the OAuthRequest
	 * @return the {@link InstagramAuthService} instance for method chaining
	 */
	public InstagramAuthService proxy(Proxy requestProxy) {
		//TODO Add Preconditions check here

		this.requestProxy = requestProxy;

		return this;
	}

	/**
	 * Returns the fully configured {@link OAuthService}
	 * 
	 * @return fully configured {@link OAuthService}
	 */
	public InstagramService build() {
		InstagramApi api = new InstagramApi();
		OAuthConfig config;

		Preconditions.checkEmptyString(apiKey, "You must provide an api key");
		Preconditions.checkEmptyString(apiSecret, "You must provide an api secret");

		config = new OAuthConfig(apiKey, apiSecret, callback, scope, display, state);

		if (this.requestProxy != null) {
			config.setRequestProxy(this.requestProxy);
		}

		return api.createService(config);
	}
}
