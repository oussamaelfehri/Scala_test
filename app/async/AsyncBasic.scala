package com.particeep.test.async

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * You have 2 webservices, we want to compute the sum of the 2 webservice call.
 *
 * You need to write only the compute function.
 * For instance : compute(1) should return 1099 (1098 + 1)
 *
 * It's part of the exercise to handle error cases
 */
object AsyncBasic {

  def compute(id: String): Future[Int] = {
    val result1F = Webservice1.call(id)
    val result2F = Webservice2.call(id)

    for {
      result1 <- result1F
      result2 <- result2F
    } yield {
      val value1 = result1.getOrElse(0)
      val value2 = result2 match {
        case Right(v) => v
        case Left(_)  => 0
      }
      value1 + value2
    }
  }
}

object Webservice1 {
  private[this] val result = Map(
    "1"  -> 1,
    "2"  -> 21,
    "5"  -> 4,
    "10" -> 1987
  )

  def call(id: String): Future[Option[Int]] = Future(result.get(id))
}

object Webservice2 {
  private[this] val result = Map(
    "1"  -> 1098,
    "3"  -> 218777,
    "9"  -> 434,
    "10" -> Int.MaxValue
  )

  def call(id: String): Future[Either[String, Int]] = Future {
    result.get(id) match {
      case Some(x) => Right(x)
      case None    => Left("No value")
    }
  }
}
