package d4d30.groovlet.taglib


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

}