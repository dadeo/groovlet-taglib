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

import d4d30.groovlet.taglib.internal.AbstractTagLibGroovyServlet
import d4d30.groovlet.taglib.internal.ClassNameInputParameterSplitter
import javax.servlet.ServletConfig

class TagLibGroovyServlet extends AbstractTagLibGroovyServlet {
  private ClassNameInputParameterSplitter classNameInputParameterSplitter = new ClassNameInputParameterSplitter()

  @Override
  protected Collection<Object> findTagLibs(ServletConfig config) {
    def tagLibNames = classNameInputParameterSplitter.split(config.getInitParameter('tagLibs'))
    tagLibNames.collect { Class.forName(it).newInstance() }
  }

}