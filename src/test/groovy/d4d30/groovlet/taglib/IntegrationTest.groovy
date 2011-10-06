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

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException

class IntegrationTest extends AbstractServerTest {

  void test_groovlet_page_displayed() {
    web.do {
      assert heading.text == "hello groovlet world!!!!"
    }
  }

  void test_tag_invoked() {
    web.do {
      assert date1.text == '01-03-2010'
      assert date2.text == '2010-01-03'
    }
  }

  void test_request_parameter_not_specified() {
    web.do {
      assert parameter_firstName.text == ''
    }
  }

  void test_request_parameter_accessible() {
    openPage(firstName: 'pinky')
    web.do {
      assert parameter_firstName.text == 'pinky'
    }
  }

  void test_response_accessible() {
    try {
      openPage 'error.groovy'
      fail('should fail with 400 response code')
    }
    catch (FailingHttpStatusCodeException e) {
      assert e.message == '400 Forced Error for http://localhost:8081/error.groovy'
    }
  }

}