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

import groovy.servlet.GroovyServlet
import groovy.servlet.ServletBinding
import org.springframework.web.servlet.FrameworkServlet
import javax.servlet.ServletConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.lang.reflect.Field


class TagLibGroovyServlet extends GroovyServlet {
  private FrameworkServlet frameworkServlet

  @Override
  void init(ServletConfig config) {
    super.init(config)
    frameworkServlet = new FrameworkServlet() {
      @Override
      protected void doService(HttpServletRequest request, HttpServletResponse response) {
      }
    }
    frameworkServlet.init(config)
  }

  @Override
  protected void setVariables(ServletBinding binding) {
    frameworkServlet.webApplicationContext.getBeansWithAnnotation(TagLib).each { name, bean ->
      bean.class.declaredFields.each { Field field ->
        if(field.getAnnotation(Tag)) {
          Closure closure = bean[field.name].clone()
          closure.delegate = binding
          binding.setVariable(field.name, closure)
        }
      }
    }
  }

}