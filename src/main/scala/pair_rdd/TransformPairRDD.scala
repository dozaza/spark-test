package pair_rdd

import org.apache.spark.rdd.RDD

object TransformPairRDD {

  // group rdd with same key and apply function on each element
  def reduceByKey(rdd: RDD[(Int, Int)]): RDD[(Int, Int)] = {
    rdd.reduceByKey{case (i1, i2) => i1 + i2 }
  }

  def mapValue(rdd: RDD[(Int, Int)]): RDD[(Int, Int)] = {
    rdd.mapValues(_ + 1)
  }

  def flatMapValues(rdd: RDD[(Int, List[Int])]): RDD[(Int, Int)] = {
    rdd.flatMapValues(l => l)
  }

  def substractByKey(rdd1: RDD[(Int, Int)], rdd2: RDD[(Int, Int)]): RDD[(Int, Int)] = {
    // remove all keys in rdd1 which exist in rdd2
    rdd1.subtract(rdd2)
  }

  def filterByValue(rdd: RDD[(Int, Int)]): RDD[(Int, Int)] = {
    rdd.filter(_._2 > 5)
  }
}
