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

import com.google.inject.Inject;
import play.Configuration;
import play.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by The Ranger (ranger@risk.ee) on 2016-08-20
 * for Baltnet Communications LLC (info@baltnet.ee)
 */
public class TrustedSSLContext {

	private static final String CONF_TRUST_STORES = "WSDL2Java.trustStores";
	private static final String CONF_TRUST_STORES_PATH = "path";
	private static final String CONF_TRUST_STORES_PASSWORD = "password";
	private static final String DEFAULT_PASSWORD = "changeit";

	@Inject
	public TrustedSSLContext(Configuration configuration) {
		List<Configuration> list = configuration.getConfigList(CONF_TRUST_STORES);
		if (list == null || list.isEmpty()) {
			Logger.warn("TrustStore configuration list is empty. Using default SSL context.");
			return;
		}

		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");

			List<TrustManager> trustManagers = new ArrayList<>();
			for (Configuration config : list) {
				String path = config.getString(CONF_TRUST_STORES_PATH, "");
				String password = config.getString(CONF_TRUST_STORES_PASSWORD, DEFAULT_PASSWORD);
				if (path.isEmpty()) {
					Logger.warn("Key store path not configured, skipping loading trust store entry");
					continue;
				}

				try {
					trustManagers.add(createTrustManager(path, password));
				}
				catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException ex) {
					Logger.warn("Could not load trust store from file " + path + ", skipping");
				}
			}
			sslContext.init(null, trustManagers.toArray(new TrustManager[trustManagers.size()]), null);
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			Logger.debug("Enabled custom SSL Context");
		}
		catch (NoSuchAlgorithmException | KeyManagementException ex) {
			Logger.error("Failed initializing trusted SSL context: " + ex.getMessage());
			Logger.error("Using default SSL context instead. This may cause application to misbehave if making HTTPS connections.");
		}
	}

	private TrustManager createTrustManager(String filePath, String password) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		KeyStore ts = KeyStore.getInstance(KeyStore.getDefaultType());

		FileInputStream in = new FileInputStream(new File(filePath));
		ts.load(in, password.toCharArray());
		in.close();

		tmf.init(ts);
		return tmf.getTrustManagers()[0];
	}
}
