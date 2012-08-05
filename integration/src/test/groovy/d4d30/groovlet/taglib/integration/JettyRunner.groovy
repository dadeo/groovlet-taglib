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
package d4d30.groovlet.taglib.integration

import d4d30.groovlet.taglib.standard.TagLibGroovyServlet
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.webapp.WebAppContext

public class JettyRunner {
  private final String CONTEXT_PATH = "/"
  private final String WEB_APP_DIRECTORY = "webapp"

  private Server server
  private Map options
  private List servletConfigurations = []

  JettyRunner(options = [:]) {
    if (!options.port) options.port = 8081
    if (!options.webXml) options.webXml = "web.xml"
    this.options = options
  }

  def start() {
    final URL warUrl = this.class.getClassLoader().getResource(WEB_APP_DIRECTORY)
    final URL webXml = this.class.getClassLoader().getResource(WEB_APP_DIRECTORY + "/WEB-INF/${options.webXml}");

    WebAppContext context = new WebAppContext(warUrl.toExternalForm(), CONTEXT_PATH)
    context.descriptor = webXml.toExternalForm()

    servletConfigurations.each { servletConfiguration ->
      ServletHolder springServletHolder = new ServletHolder(servletConfiguration.servletClass)
      servletConfiguration.initParams.each { initParam ->
        springServletHolder.setInitParameter(initParam.key, initParam.value)
      }
      context.addServlet(springServletHolder, servletConfiguration.pathSpec);
    }

    server = new Server(options.port);
    server.setHandler(context);
    server.start()
  }

  def stop() {
    server.stop()
  }

  def addServlet(Class servletClass, String pathSpec, Map initParams) {
    servletConfigurations << [servletClass: servletClass, pathSpec: pathSpec, initParams: initParams]
  }

  static void main(args) {
    JettyRunner runner = new JettyRunner()
    runner.addServlet(TagLibGroovyServlet, '*.groovy', [tagLibs: 'd4d30.groovlet.taglib.integration.support.TestTagLib'])
//    runner.addServlet(SpringTagLibGroovyServlet, '*.groovy', [contextConfigLocation: 'classpath:/contexts/taglib.xml'])
    runner.start()
    runner.server.join()
  }

}
