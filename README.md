
Better Configurations for Large-Scale Systems (BestConf)
====================================

Bestconf can find better configurations for a specific large-scale system under a given application workload.

Given the diverse application workloads, a large-scale system with hundreds of configuration parameters, such as Spark, 
Hadoop, Cassandra, MySQL and Tomcat, does not necessarily offer the best performance under their default configurations.

Currently, Bestconf has been tested on the following systems: 

-------->>>[Spark](http://spark.apache.org/)

-------->>>[Cassandra](http://cassandra.apache.org/)

-------->>>[Hive](http://hive.apache.org/) + [Hadoop](http://hadoop.apache.org/)

-------->>>[Tomcat Server](http://tomcat.apache.org/)

-------->>>[MySQL](https://www.mysql.com/)


Links
-----

zhuyuqing@ict.ac.cn

liujianxun@ict.ac.cn


Getting Started
---------------

1. Download the [latest release of BestConf](https://github.com/zhuyuqing/bestconf/archive/master.zip):

    ```sh
    curl -O --location https://github.com/zhuyuqing/bestconf/archive/master.zip
    unzip bestconf-master.zip
    cd bestconf-master/deploy
    ```
    
2. Set up a system for tuning. We offer two deployable examples in the project: one is called 
   4BI(Hive+Hadoop under Hibench tests) and the other is called 4tomecat(Tomcat under JMeter tests).

3. Run BestConf command. 

    On Linux:
    Update all system and deployment related scripts accordingly and move them to the
    correct path on the servers.
    Move the system-specific jar file to lib. (For example, move deploy/4BI/bestconfBI.jar
    to deploy/lib)
    ```sh
    cd deploy
    bin/start.sh
    ```
Building from source
--------------------

Import the whole Bestconf project into eclipse and enjoy!~
