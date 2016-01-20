package code.comet

import java.util.Date

import net.liftweb.common.Full
import net.liftweb.http.CometActor
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Schedule
import net.liftweb.util.TimeHelpers.now

import scala.xml.Text

class Clock extends CometActor {
  override def defaultPrefix = Full("clk")

  def render = bind("time" -> timeSpan)

  def timeSpan = <span id="time">{now}</span>

  // schedule a ping every 10 seconds so we redraw
  Schedule.schedule(this, Tick, 1000L)

  override def lowPriority : PartialFunction[Any, Unit] = {
    case Tick => {
      println("Got tick " + new Date());
      
      // send a little Javascript to the browser to set the contents of
      // the element with an id of 'time' to the current time.
      partialUpdate(SetHtml("time", Text(now.toString)))
      // schedule an update in 10 seconds
      Schedule.schedule(this, Tick, 1000L)
    }
  }
}
case object Tick
