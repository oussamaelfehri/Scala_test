package com.particeep.test.akka

import akka.actor.Actor
import scala.concurrent.Future
import scala.util.{ Failure, Success }
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Do you see anything that could lead to potential problems ?
 * What would you do to fix it ?
 * Do not mind about the not implemented code
 */
class WhatsWrong3 extends Actor {

  var internalState = "internal state"

  def receive: Receive = {
    case "a query" =>
      val requestF: Future[String] = queryAsyncServer()
      requestF.onComplete {
        case Success(r) => self ! r
        case Failure(e) => e.printStackTrace()
      }
    case response: String => handleResponse(response)
  }

  def handleResponse(r: String): Unit = {
    internalState = r // mutate internal state
  }

  def queryAsyncServer(): Future[String] = Future {
    "response from server"
  }
}
