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
 
## What it does 
 * It compiles **/tmp/TestInit.cpp** into a dynamic library, which can be then called by JNA (Java Native Access)
 * It then broadcasts the compiled library to each worker node.
 * Each worker node calls the method **pushItem** to give the library the entries in the RDD one by one.
 * Each worker then calls **getResult** to get result.
 
