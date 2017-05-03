# Lift Starter

Install [SBT](http://www.scala-sbt.org/download.html)

Download this project by cloning it or using the zip download link.

On the command line run change directory to the project root and run:
```
  sbt
```
then
```
  jetty:start
```  
then go to [http://127.0.0.1:8080/](http://127.0.0.1:8080/)


## How does it work?

Lift apps use the standard servlet framework

See web.xml where the Liftweb filter is used to serve the app
Lift initialises itself by looking for `bootstrap.liftweb.Boot`

The website itself is built from HTML templates in which Lift Snippets and Comet actors are embedded.
See `index.html` for an example that has a snippet with Ajax, and a CometActor that receives updates pushed
from the server.

This project also has a rest example in `code.rest.RestExample`

For more information see:

* [Liftweb Cookbook](http://chimera.labs.oreilly.com/books/1234000000030/index.html)
* [Simply Lift](https://simply.liftweb.net/)
* [Buliding Web Applications using Lift](http://www.drdobbs.com/web-development/building-web-applications-with-lift/240159451)