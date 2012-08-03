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
package d4d30.groovlet.taglib.core.internal

import groovy.servlet.GroovyServlet
import groovy.servlet.ServletBinding
import javax.servlet.ServletConfig

abstract class AbstractTagLibGroovyServlet extends GroovyServlet {
  private TagLibListTagExtractor tagLibListTagExtractor = new TagLibListTagExtractor()
  private Map<String, Closure> tags = [:]

  @Override
  void init(ServletConfig config) {
    super.init(config)
    Collection<Object> tagLibs = findTagLibs(config)
    tags = tagLibListTagExtractor.extractFrom(tagLibs as List)
  }

  abstract protected Collection<Object> findTagLibs(ServletConfig config)

  @Override
  protected void setVariables(ServletBinding binding) {
    binding.setVariable('tag', new TagWrapper(tags, binding))
//    tags.each { tagName, tagClosure ->
//      Closure closure = tagClosure.clone()
//      closure.delegate = binding
//      binding.setVariable(tagName, closure)
//    }
  }

  class TagWrapper implements GroovyInterceptable {
    private Map<String, Closure> tags = [:]
    private binding

    TagWrapper(tags, binding) {
      this.binding = binding
      this.tags = tags
    }

    @Override
    Object invokeMethod(String name, Object args) {
      Closure closure = tags[name].clone()
      closure.delegate = binding
      closure(*args)
    }

  }
}