/*
 * Copyright 2016 Baltnet Communications LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ee.risk.play.modules.wsdl2java;

import play.Configuration;
import play.Logger;

import javax.inject.Inject;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by The Ranger (ranger@risk.ee) on 2016-08-28
 * for Baltnet Communications LLC (info@baltnet.ee)
 */
public class BasicAuthenticator extends Authenticator {

	private static final String CONF_AUTH_USER = "WSDL2Java.httpBasicAuth.user";
	private static final String CONF_AUTH_PASSWORD = "WSDL2Java.httpBasicAuth.password";
	private String authUser;
	private String authPassword;

	@Inject
	public BasicAuthenticator(Configuration configuration) {
		authUser = configuration.getString(CONF_AUTH_USER, "");
		authPassword = configuration.getString(CONF_AUTH_PASSWORD, "");
		if (authUser.isEmpty() || authPassword.isEmpty()) {
			Logger.warn("HTTP Basic authentication configuration missing. Not enabling custom HTTP Basic authenticator.");
			return;
		}

		Logger.info("Loading custom HTTP Basic authenticator.");
		Authenticator.setDefault(this);
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(authUser, authPassword.toCharArray());
	}
}
