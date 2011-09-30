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

  void test_setVariables_sets_binding_as_tags_delegate() {
    assert binding.is(binding.getVariable('foo1').delegate)
  }

  void test_setVariables_clones_tag() {
    def binding_1_foo = binding.getVariable('foo1')
    def binding_2_foo = createBindingAndSetVariables().getVariable('foo1')

    assert binding_1_foo.delegate != binding_2_foo.delegate
  }

  @TagLib
  static class FooTagLib {
    @Tag def foo1 = { 'FOO1' }
    @Tag def foo2 = { 'FOO2' }
    def foo3 = { 'FOO3' }
  }

  @TagLib
  static class BarTagLib {
    @Tag def bar1 = { 'BAR1' }
  }

  private def assertTagExists(String tagName) {
    def tag = binding.getVariable(tagName)
    assert tag() == tagName.toUpperCase()
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

    // The combination of AnnotationConfigWebApplicationContext and contextConfigLocation will cause
    // static inner classes of this test to be picked up when properly annotated as components
    MockServletConfig servletConfig = new MockServletConfig('taglib')
    servletConfig.addInitParameter('contextConfigLocation', 'd4d30.groovlet.taglib')
    servletConfig.addInitParameter('contextClass', 'org.springframework.web.context.support.AnnotationConfigWebApplicationContext')
    servlet.init(servletConfig)
    servlet
  }
}
