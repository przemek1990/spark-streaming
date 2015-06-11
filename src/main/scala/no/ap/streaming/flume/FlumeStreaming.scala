package no.ap.streaming.flume

import com.typesafe.config.ConfigFactory
import no.ap.streaming.event.RootEvent
import no.ap.streaming.event.RootProtocol._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import spray.json._

object FlumeStreaming {

  def main(args: Array[String]) {

    val batchInterval = Milliseconds(20000)
    val conf = ConfigFactory.load()

    // Create the context and set the batch size
    val sparkConf = new SparkConf().setAppName("FlumeEventCount")
    val ssc = new StreamingContext(sparkConf, batchInterval)
    ssc.checkpoint("s3n://" + conf.getString("aws.access.key") + ":" + conf.getString("aws.secret.key") + "@stream-checkpoint/spark")
    // Create a flume stream
    val events = FlumeUtils.createPollingStream(ssc, conf.getString("flume.sink.host"), conf.getInt("flume.sink.port"))

    // Print out the count of events received from this server in each batch
    events.count().map(cnt => "Received " + cnt + " flume events.").print()

    events.flatMap(event => {
      new String(event.event.getBody().array(), "UTF-8").parseJson.convertTo[List[RootEvent]]
    }).map(rootEvent => (rootEvent.eventType, 1)).reduceByKey((x, y) => x + y).print()

    ssc.start()
    ssc.awaitTermination()
  }
}


