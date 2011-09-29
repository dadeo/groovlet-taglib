package d4d30.groovlet.taglib

@TagLib
class TestTagLib {

  @Tag
  def date = { options ->
    Date date = Date.parse('MM/dd/yyyy', '01/03/2010')
    html.span id: options.id, date.format(options.format)
  }
}