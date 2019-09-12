package gravitrips

import gravitrips.frontend.{ConsoleFrontend, Frontend}

object Game extends App {
  implicit val frontend: Frontend = ConsoleFrontend
  Loop()
}
