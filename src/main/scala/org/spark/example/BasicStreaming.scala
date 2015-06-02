package org.spark.example

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object BasicStreaming {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("SocketStream")
    val ssc = new StreamingContext(conf, Seconds(5))

    val lines = ssc.socketTextStream("localhost", 8888)
    val errorLines = lines.filter(_.contains("error"))

    //print errorLines
    errorLines.print()
    //save errorLines to files
    errorLines.saveAsTextFiles("/home/ec2-user/app//errors")

    //start streaming context
    ssc.start()
    //wait for job finished
    ssc.awaitTermination()

  }
}
