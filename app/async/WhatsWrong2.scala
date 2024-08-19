package com.particeep.test.async

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class CEO(id: String, first_name: String, last_name: String)
case class Enterprise(id: String, name: String, ceo_id: String)

object CEODao {
  val ceos = List(
    CEO("1", "Mark", "Zuckerberg"),
    CEO("2", "Sundar", "Pichai")
  )

  def byId(id: String): Future[Option[CEO]] = Future(ceos.find(_.id == id))
}

object EnterpriseDao {
  val enterprises = List(
    Enterprise("1", "Google", "1"),
    Enterprise("2", "Facebook", "2")
  )

  def byId(id: String): Future[Option[Enterprise]] = Future(enterprises.find(_.id == id))
  def byCEOId(ceo_id: String): Future[Option[Enterprise]] = Future(enterprises.find(_.ceo_id == ceo_id))
}

object WhatsWrong2 {

  def getCEOAndEnterprise(ceo_id: Option[String]): Future[(Option[CEO], Option[Enterprise])] = {
    ceo_id match {
      case Some(id) =>
        val ceoFuture = CEODao.byId(id).recover { case _ => None }
        val enterpriseFuture = EnterpriseDao.byCEOId(id).recover { case _ => None }

        for {
          ceo <- ceoFuture
          enterprise <- enterpriseFuture
        } yield {
          (ceo, enterprise)
        }

      case None => 
        Future.successful((None, None))
    }
  }
}
