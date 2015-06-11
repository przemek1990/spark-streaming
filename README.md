# spark-streaming

Spark streaming example in Scala

# Buliding 
Spark streaming is building using SBT
    
    sbt assemble
    
    
# Configuration 
Create application.properties file in the resources dir with below configuration:

    flume.sink.host=FLUME_SINK_HOST
    flume.sink.port=FLUME_SING_PORT
    aws.secret.key=AWS_SECRET_KEY
    aws.access.key=AWS_ACCESS_KEY

    
## Deploy 
In SPARK_HOME directory

    ./bin/spark-submit --class package.className --master MASTER_URL 
    PATH_TO_JAR/flume-streaming.jar [HOST] [PORT]



