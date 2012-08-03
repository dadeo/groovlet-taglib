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

import junit.framework.TestCase
import webdsl.WebDsl

abstract class AbstractServerTest extends TestCase {
  public static final PORT = 8081
  def web
  def server = new JettyRunner(port: PORT)

  void setUp() {
    server.start()
    openPage()
  }

  protected def openPage(String page = null) {
    openPage([:], page)
  }

  protected def openPage(Map params, String page = null) {
    def queryString = ''
    if (params) {
      queryString = '?' + params.collect { k, v -> "$k=${URLEncoder.encode(v)}"}.join('&')
    }
    web = new WebDsl().for("http://localhost:$PORT/${page ?: defaultPage()}$queryString")
  }

  protected String defaultPage() {
    return "index.groovy"
  }

  void tearDown() {
    server.stop()
  }

}