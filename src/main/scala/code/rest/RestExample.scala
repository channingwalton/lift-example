package code.rest

import net.liftweb.common.Full
import net.liftweb.http.{ForbiddenResponse, OkResponse}
import net.liftweb.http.rest.RestHelper

object RestExample extends RestHelper {

  serve {
    case Get("ok" :: _, _) ⇒ Full(OkResponse())

      // lift people often use this form in the pattern match
    case "forbidden" :: _ Get _ ⇒ ForbiddenResponse("Forbidden")
  }
}