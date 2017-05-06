# spark-native

## Compile and Run
 * First, you need to install sbt. Download from http://www.scala-sbt.org/index.html.
 * Run *sbt assembly* to compile to jar. The target file will be in the **target/scala-2.11** directory. 
 * You need to copy **c++/TestInt.cpp** to **/tmp**.
 * Run with
 > ~/spark/bin/spark-submit  \
 > --class "Test" \
 > --master spark://[Your master] \
 > [Compiled jar]
 
