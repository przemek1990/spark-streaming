# spark-streaming

Spark streaming example in Scala

# Buliding 
Spark streaming is building using SBT
    
    sbt assemble
    
## Deploy 
In SPARK_HOME directory

    ./bin/spark-submit --class className --master MASTER_URL 
    PATH_TO_JAR/flume-streaming.jar [HOST] [PORT]



