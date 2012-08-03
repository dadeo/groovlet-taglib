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

import d4d30.groovlet.taglib.core.internal.support.FooTagLib
import d4d30.groovlet.taglib.core.internal.support.NottaTagLib
import org.junit.Test

class TagLibTagExtractorTest {

  @Test
  void test_extractFrom_returns_all_closures_marked_with_Tag_annotation() {
    Map<String, Closure> tags = new TagLibTagExtractor().extractFrom(new FooTagLib())
    assert tags.keySet() == ['foo1', 'foo2'] as Set
    assert tags.foo1() == 'FOO1'
    assert tags.foo2() == 'FOO2'
  }

  @Test
  void test_extractFrom_returns_empty_map_when_no_closures_are_marked_with_Tag_annotation() {
    Map<String, Closure> tags = new TagLibTagExtractor().extractFrom(new NottaTagLib())
    assert tags.isEmpty()
  }

}