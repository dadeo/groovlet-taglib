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
package d4d30.groovlet.taglib.internal

import org.junit.Before
import org.junit.Test

class ClassNameInputParameterSplitterTest {
  private ClassNameInputParameterSplitter splitter

  @Before
  void setup() {
    splitter = new ClassNameInputParameterSplitter()
  }

  @Test
  void test_split_null_results_in_empty_list() {
    assert splitter.split(null) == []
  }

  @Test
  void test_split_empty_string_results_in_empty_list() {
    assert splitter.split('') == []
  }

  @Test
  void test_split_string_with_only_white_space_results_in_empty_list() {
    assert splitter.split("   \n    \n   ") == []
  }

  @Test
  void test_split_string_with_only_one_item() {
    def input = 'this.is.item.one'

    assert splitter.split(input) == ['this.is.item.one']
  }

  @Test
  void test_split_string_with_only_one_item_and_white_space() {
    def input = '''
        this.is.item.one
      '''

    assert splitter.split(input) == ['this.is.item.one']
  }

  @Test
  void test_split_string_with_multiple_items_on_separate_lines() {
    def input = '''
        this.is.item.one
        this.is.item.two
        this.is.item.three
      '''

    assert splitter.split(input) == ['this.is.item.one', 'this.is.item.two', 'this.is.item.three']
  }

  @Test
  void test_split_string_with_multiple_items_on_same_lines() {
    def input = '''
        this.is.item.one this.is.item.two this.is.item.three
      '''

    assert splitter.split(input) == ['this.is.item.one', 'this.is.item.two', 'this.is.item.three']
  }

}