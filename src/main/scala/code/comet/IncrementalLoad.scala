package code.comet

import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JsExp.strToJsExp
import net.liftweb.http.js.jquery.JqJE.{JqAppend, JqId}
import net.liftweb.http.{CometActor, RenderOut, SHtml}

import scala.xml.NodeSeq

class IncrementalLoad extends CometActor {

  case class Rows(rows: List[Int])

  // assume a big table of data
  val bigData: Rows = Rows((0 until 100).toList)

  // this method is called when the page first loads
  override def render: RenderOut = {
    // send the data to this actor to get things going
    this ! bigData

    // return the html that will have rows appended
    <div id="theTable">
    </div>
  }

  // this method handles messages sent to the actor
  override def lowPriority: PartialFunction[Any, Unit] = {

    case Rows(data) ⇒ waitForBrowser {
      val (chunk, rest) = data.splitAt(10)
      val html = chunk.map(i ⇒ <div>Row{i}</div>)

      // send the chunk
      partialUpdate(appendChild("theTable", html))

      if (rest.nonEmpty) this ! Rows(rest)
    }
  }

  // append some html as a child to an element
  def appendChild(id: String, body: NodeSeq): JsCmd =
    JqId(id) ~> JqAppend(body)

  /* This takes a function which will be invoked after
   * the browser calls back to the server.
   *
   * Doing this allows the server to be sure
   * that the browser is free before pushing
   * the next chunk
   */
  def waitForBrowser(f: ⇒ Unit): Unit =
    partialUpdate(SHtml.ajaxInvoke(() ⇒ f))
}
