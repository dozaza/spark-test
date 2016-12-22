import org.apache.spark.rdd.RDD

class TransformPairRDD {

  // group rdd with same key and apply function on each element
  def reduceByKey(rdd: RDD[(Int, Int)]): RDD[(Int, Int)] = {
    rdd.reduceByKey{case (i1, i2) => i1 + i2 }
  }

  def groupByKey(rdd: RDD[(Int, Int)]): RDD[(Int, List[(Int, Int)])] = {
    rdd.groupBy(_._1).mapValues(_.toList)
  }

  def combineByKey(rdd1: RDD[(Int, Int)], rdd2: RDD[(Int, Int)]): RDD[(Int, (Int, Int))] = {
    // Calculate every key's sum & count of value
    rdd1.combineByKey(
      i => (i, 1),    // initial value
      (t: (Int, Int), i) => (t._1 + i, t._2 + 1),   // apply on every element in partition
      (t1: (Int, Int), t2: (Int, Int)) => (t1._1 + t2._2, t1._2 + t2._2)    // apply between partitions
    )
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

  def innerJoin(rdd1: RDD[(Int, Int)], rdd2: RDD[(Int, Int)]): RDD[(Int, (Int, Int))] = {
    // only join keys both exists in 2 rdds, and return key -> (value_in_rdd1, value_in_rdd2)
    rdd1.join(rdd2)
  }

  def rightJoin(rdd1: RDD[(Int, Int)], rdd2: RDD[(Int, Int)]): RDD[(Int, (Option[Int], Int))] = {
    rdd1.rightOuterJoin(rdd2)
  }

  def leftJoin(rdd1: RDD[(Int, Int)], rdd2: RDD[(Int, Int)]): RDD[(Int, (Int, Option[Int]))] = {
    rdd1.leftOuterJoin(rdd2)
  }

  def cogroup(rdd1: RDD[(Int, Int)], rdd2: RDD[(Int, Int)]): RDD[(Int, (Iterable[Int], Iterable[Int]))] = {
    // like first rdd1.groupBy(_._1).join(rdd2.groupBy(_._1))nn
    rdd1.cogroup(rdd2)
  }

  def filterByValue(rdd: RDD[(Int, Int)]): RDD[(Int, Int)] = {
    rdd.filter(_._2 > 5)
  }
}