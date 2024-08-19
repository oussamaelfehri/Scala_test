package com.particeep.test.basic

/**
 * What is the complexity of the function ?
 *
 * Refactor it to be a better function
 */
object Refactoring {

  case class File(
    name:     String,
    category: String
  )

  def getCategories(files: List[File]): List[String] = {
    if (files == null) List()
    else files.flatMap(file => Option(file.category)).distinct
  }
}
