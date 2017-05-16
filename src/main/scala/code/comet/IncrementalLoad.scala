package code.comet

import java.util.concurrent.ScheduledFuture

import net.liftweb.http.js.jquery.JqJE.{JqAppend, JqId}
import net.liftweb.http.js.{JsCmd, JsCmds}
import net.liftweb.http.{CometActor, RenderOut, SHtml}
import net.liftweb.util.Schedule

import scala.xml.{NodeSeq, Text}
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JsExp.strToJsExp

class IncrementalLoad extends CometActor {

  case class Rows(rows: List[Int])

  // assume a big table of data
  val bigData: Rows = Rows((0 until 1000).toList)

  override def render: RenderOut = {
    scheduleNextPush(bigData)

    // return the html that will have rows appended
    <div id="theTable">
    </div>
  }

  private def scheduleNextPush(data: Rows): ScheduledFuture[Unit] = {
    Schedule.perform(this, data, 1000)
  }

  override def lowPriority : PartialFunction[Any, Unit] = {
    case Rows(data) ⇒ waitForBrowser {
      val (chunk, rest) = data.splitAt(10)
      val html = chunk.map(i ⇒ <div>Row
        {i}
      </div>)

      // send the chunk
      partialUpdate(appendChild("theTable", html))

      if (rest.nonEmpty) scheduleNextPush(Rows(rest))
    }
  }

  def appendChild(id: String, body: NodeSeq): JsCmd = JqId(id) ~> JqAppend(body)

  def waitForBrowser(f: ⇒ Unit): Unit = partialUpdate(SHtml.ajaxInvoke(() ⇒ f))
}
