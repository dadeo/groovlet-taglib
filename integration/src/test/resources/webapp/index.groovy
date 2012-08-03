package webapp
html.html {
  body {
    h1 id: 'heading', 'hello groovlet world!!!!'
    br()
    tag.date id: 'date1', format: 'MM-dd-yyyy'
    br()
    tag.date id: 'date2', format: 'yyyy-MM-dd'
    br()
    p id: 'parameter_firstName', request.getParameter('firstName')
  }
}