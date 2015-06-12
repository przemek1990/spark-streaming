# spark-streaming

Example how to integrate Apache Flume with Apache Spark using JSON data format. 

# Buliding 
Spark streaming is building using SBT
    
    sbt assemble
    
Example classes are located in no.ap.streaming.flume package.     
    
# Configuration 
Configure your app with below property:

    flume.sink.host=FLUME_SINK_HOST
    flume.sink.port=FLUME_SING_PORT
    aws.secret.key=AWS_SECRET_KEY
    aws.access.key=AWS_ACCESS_KEY

You can choose the most convenient method for you:

    system properties
    application.conf (all resources on classpath with this name)
    application.json (all resources on classpath with this name)
    application.properties (all resources on classpath with this name)
    reference.conf (all resources on classpath with this name)
  
For more information see https://github.com/typesafehub/config

## Deploy 
In SPARK_HOME directory

    ./bin/spark-submit --class package.className --master MASTER_URL 
    PATH_TO_JAR/spark-streaming.jar 



