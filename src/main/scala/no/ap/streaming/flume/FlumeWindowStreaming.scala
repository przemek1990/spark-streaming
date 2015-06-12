package no.ap.streaming.flume

import com.typesafe.config.ConfigFactory
import no.ap.streaming.event.ActivityStreamEvent
import no.ap.streaming.event.ActivityStreamProtocol._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, Milliseconds, StreamingContext}
import spray.json._


object FlumeWindowStreaming {
  def main(args: Array[String]) {
    //10seconds batch interval
    val batchInterval = Milliseconds(10000)
    //load configuration
    val conf = ConfigFactory.load()

    // Create the context and set the batch size
    val sparkConf = new SparkConf().setAppName("FlumeEventCount")
    val ssc = new StreamingContext(sparkConf, batchInterval)
    ssc.checkpoint("s3n://" + conf.getString("aws.access.key") + ":" + conf.getString("aws.secret.key") + "@stream-checkpoint/spark")
    // Create a flume stream
    val events = FlumeUtils.createPollingStream(ssc, conf.getString("flume.sink.host"), conf.getInt("flume.sink.port"))
    //last 3 batches
    val eventWindows = events.window(Seconds(30),Seconds(10))
    // Print out the count of events received from this server in each batch
    eventWindows.count().map(cnt => "Received " + cnt + " flume events.").print()

    eventWindows.flatMap(event => {
      new String(event.event.getBody().array(), "UTF-8").parseJson.convertTo[List[ActivityStreamEvent]]
    }).map(rootEvent => (rootEvent.eventType, 1)).reduceByKey((x, y) => x + y).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
