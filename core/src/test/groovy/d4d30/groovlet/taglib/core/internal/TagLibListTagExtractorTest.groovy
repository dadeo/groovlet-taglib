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

import d4d30.groovlet.taglib.core.internal.support.BarTagLib
import d4d30.groovlet.taglib.core.internal.support.FooTagLib
import groovy.mock.interceptor.MockFor
import org.junit.Test

class TagLibListTagExtractorTest {

  @Test
  void test_extractFrom_returns_all_closures_marked_with_Tag_annotation() {
    FooTagLib fooTagLib = new FooTagLib()
    BarTagLib barTagLib = new BarTagLib()

    def mockTagLibTagExtractor = new MockFor(TagLibTagExtractor)

    mockTagLibTagExtractor.demand.extractFrom {
      assert it == fooTagLib
      [foo: {'f'}]
    }

    mockTagLibTagExtractor.demand.extractFrom {
      assert it == barTagLib
      [bar: {'b'}]
    }

    mockTagLibTagExtractor.use {
      def extractor = new TagLibListTagExtractor()
      Map<String, Closure> tags = extractor.extractFrom([fooTagLib, barTagLib])
      assert tags.keySet() == ['foo', 'bar'] as Set
      assert tags.foo() == 'f'
      assert tags.bar() == 'b'
    }
  }

  @Test
  void test_extractFrom_returns_empty_map_when_no_tablibs_specified() {
    Map<String, Closure> tags = new TagLibListTagExtractor().extractFrom([])
    assert tags != null
    assert tags.isEmpty()
  }

}