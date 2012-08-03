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
package d4d30.groovlet.taglib.spring

import d4d30.groovlet.taglib.core.internal.AbstractTagLibGroovyServlet
import javax.servlet.ServletConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.FrameworkServlet

class SpringTagLibGroovyServlet extends AbstractTagLibGroovyServlet {
  private FrameworkServlet frameworkServlet

  protected Collection<Object> findTagLibs(ServletConfig config) {
    frameworkServlet = new FrameworkServlet() {
      @Override
      protected void doService(HttpServletRequest request, HttpServletResponse response) {
      }
    }
    frameworkServlet.init(config)
    frameworkServlet.webApplicationContext.getBeansWithAnnotation(TagLib).values()
  }

  @Override
  void destroy() {
    super.destroy()
    frameworkServlet.destroy()
  }

}