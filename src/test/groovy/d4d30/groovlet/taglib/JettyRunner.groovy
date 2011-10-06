/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package d4d30.groovlet.taglib

import org.mortbay.jetty.Server
import org.mortbay.jetty.webapp.WebAppContext

public class JettyRunner {
  private final String CONTEXT_PATH = "/"
  private final String WEB_APP_DIRECTORY = "webapp"

  private Server server

  JettyRunner(options) {
    if (options == null) {
      options = [port: 8081]
    }

    final URL warUrl = this.class.getClassLoader().getResource(WEB_APP_DIRECTORY);
    final String warUrlString = warUrl.toExternalForm();

    server = new Server(options.port);
    server.setHandler(new WebAppContext(warUrlString, CONTEXT_PATH));
  }

  def start() {
    server.start()
  }

  def stop() {
    server.stop()
  }

  static void main(args) {
    JettyRunner runner = new JettyRunner()
    runner.start()
    runner.server.join()
  }

}
