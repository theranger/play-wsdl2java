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

import com.google.inject.AbstractModule;

/**
 * Created by The Ranger (ranger@risk.ee) on 2016-08-20
 * for Baltnet Communications LLC (info@baltnet.ee)
 */
public class SSLContextModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(TrustedSSLContext.class).asEagerSingleton();
	}
}
