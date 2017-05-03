package code.comet

import net.liftweb.http.{CometActor, RenderOut}
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Schedule
import net.liftweb.util.TimeHelpers.now

import scala.xml.{Elem, Text}

class Clock extends CometActor {

  case object Tick

  // schedule a Tick periodically
  private val updatePeriod = 1000L
  Schedule.schedule(this, Tick, updatePeriod)

  def render: RenderOut =
    "time" #> timeSpan

  def timeSpan: Elem =
    <span id="time">{now}</span>

  override def lowPriority : PartialFunction[Any, Unit] = {
    case Tick =>
      // send a little Javascript to the browser to set the contents of
      // the element with an id of 'time' to the current time.
      partialUpdate(SetHtml("time", Text(now.toString)))
      
      // schedule another update in 1 second
      Schedule.schedule(this, Tick, updatePeriod)
  }
}
