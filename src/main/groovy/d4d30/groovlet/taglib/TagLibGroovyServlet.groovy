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