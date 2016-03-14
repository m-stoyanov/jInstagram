package org.jinstagram.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.jinstagram.auth.exceptions.OAuthException;
import org.jinstagram.auth.model.Constants;
import org.jinstagram.auth.model.OAuthConfig;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.http.Verbs;
import org.jinstagram.utils.Preconditions;

import static org.jinstagram.http.URLUtils.formURLEncode;

public class InstagramApi {
	public String getAccessTokenEndpoint() {
		return Constants.ACCESS_TOKEN_ENDPOINT;
	}

	public Verbs getAccessTokenVerb() {
		return Verbs.POST;
	}

	public String getAuthorizationUrl(OAuthConfig config) {
		Preconditions.checkValidUrl(config.getCallback(),
				"Must provide a valid url as callback. Instagram does not support OOB");

		String authorizationUrl = String.format(Constants.AUTHORIZE_URL, config.getApiKey(), formURLEncode(config.getCallback())); 
		// Append scope and/or state if present
		if (config.hasScope()) {
			authorizationUrl = authorizationUrl + String.format(Constants.SCOPED_AUTHORIZE_URL_PARAM, formURLEncode(config.getScope()));		
		} 
		if(config.hasState()){
			authorizationUrl = authorizationUrl + String.format(Constants.STATE_AUTHORIZE_URL_PARAM, formURLEncode(config.getState()));
		}			
		
		return authorizationUrl;
	}

	public AccessTokenExtractor getAccessTokenExtractor() {
		return new AccessTokenExtractor() {
			@Override
			public Token extract(String response) {
				Preconditions.checkEmptyString(response, "Cannot extract a token from a null or empty String");

				try {
					JsonParser parser = new JsonParser();
					JsonObject obj = parser.parse(response).getAsJsonObject();
					String token = null;

					if(obj.has("access_token")) {
						token = obj.get("access_token").getAsString();
					}

					if(StringUtils.isEmpty(token)) {
						throw new OAuthException("Cannot extract an acces token. Response was: " + response);
					}
					return new Token(token, "", response);
				} catch(JsonParseException e) {
					throw new OAuthException("Cannot extract an acces token. Response was: " + response);
				}
			}
		};
	}

	public InstagramService createService(OAuthConfig config) {
		return new InstagramService(this, config);
	}
}
