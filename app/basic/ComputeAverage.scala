package com.particeep.test.basic

/**
 * Compute the average of the list
 *
 * ex : [1, 10, 16] -> 9
 */
object ComputeAverage {

  def average(l: List[Double]): Double = {
    if (l.isEmpty) 0.0
    else l.sum / l.length
  }
}
