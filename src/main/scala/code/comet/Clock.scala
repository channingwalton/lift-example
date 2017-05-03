package code.comet

import java.util.Date

import net.liftweb.common.Full
import net.liftweb.http.{CometActor, RenderOut}
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Schedule
import net.liftweb.util.TimeHelpers.now

import scala.xml.{Elem, Text}

class Clock extends CometActor {

  // schedule a ping every 10 seconds so we redraw
  Schedule.schedule(this, Tick, 1000L)

  override def defaultPrefix = Full("clk")

  def render: RenderOut = "time" #> timeSpan

  def timeSpan: Elem = <span id="time">{now}</span>

  override def lowPriority : PartialFunction[Any, Unit] = {
    case Tick => {
      // send a little Javascript to the browser to set the contents of
      // the element with an id of 'time' to the current time.
      partialUpdate(SetHtml("time", Text(now.toString)))
      
      // schedule another update in 1 second
      Schedule.schedule(this, Tick, 1000L)
    }
  }
}
case object Tick
