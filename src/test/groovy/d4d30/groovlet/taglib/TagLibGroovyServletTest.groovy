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

import groovy.servlet.ServletBinding
import junit.framework.TestCase
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockServletConfig
import org.springframework.mock.web.MockServletContext

class TagLibGroovyServletTest extends TestCase {
  TagLibGroovyServlet servlet
  ServletBinding binding

  @Override
  protected void setUp() {
    servlet = createServlet()
    binding = createBindingAndSetVariables()
  }

  void test_setVariables_finds_classes_marked_as_TagLib() {
    assertTagExists 'foo1'
    assertTagExists 'bar1'
  }

  void test_setVariables_finds_fields_marked_as_Tag() {
    assertTagExists 'foo1'
    assertTagExists 'foo2'

    assertTagNotExists 'foo3'
  }

  private def assertTagExists(String tagName) {
    def tag = binding.getVariable('tag')
    assert tag."$tagName"() == tagName.toUpperCase()
  }

  private def assertTagNotExists(String tagName) {
    assert !binding.variables.containsKey(tagName)
  }

  private ServletBinding createBindingAndSetVariables() {
    ServletBinding binding = new ServletBinding(new MockHttpServletRequest(), new MockHttpServletResponse(), new MockServletContext())
    servlet.setVariables(binding)
    binding
  }

  private TagLibGroovyServlet createServlet() {
    TagLibGroovyServlet servlet = new TagLibGroovyServlet()

    MockServletConfig servletConfig = new MockServletConfig('taglib')
    servletConfig.addInitParameter('tagLibs', """
        d4d30.groovlet.taglib.support.FooTagLib
        d4d30.groovlet.taglib.support.BarTagLib
      """)
    servlet.init(servletConfig)
    servlet
  }
}
