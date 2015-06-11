name := "spark-streaming"

version := "1.0"

scalaVersion := "2.10.4"

assemblyJarName in assembly := "spark-streaming.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-streaming-flume_2.10" % "1.3.1",
  "io.spray" %% "spray-json" % "1.3.2",
  "com.typesafe" % "config" % "1.3.0",
  "org.apache.spark" % "spark-streaming_2.10" % "1.3.1" % "provided"
)