package code.snippet

import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.http.SHtml
import net.liftweb.util.Helpers._

import scala.xml.Text

class MySnippet {

  def render = {
    var count: Int = 0

    def buttonClicked(): JsCmd = {
      count = count + 1
      SetHtml("result", Text(s"You clicked me $count times"))
    }

    "button" #> SHtml.ajaxButton("Click Me", () ⇒ buttonClicked())
  }
}
