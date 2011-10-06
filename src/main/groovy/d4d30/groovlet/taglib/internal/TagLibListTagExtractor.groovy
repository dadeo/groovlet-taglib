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


class TagLibListTagExtractor {
  private TagLibTagExtractor extractor = new TagLibTagExtractor()

  Map<String, Closure> extractFrom(List<Object> tagLibs) {
    def result = [:]
    tagLibs.each { tagLib ->
      result.putAll extractor.extractFrom(tagLib)
    }
    result
  }
}