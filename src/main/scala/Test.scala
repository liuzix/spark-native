import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.liuzix.SparkNative._


object Test {
  def main(args: Array[String]) {
    
    val logFile = "s3n://spark-native-test/README" // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)
    sc.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAICRKWIRNQ7O3463A")
    sc.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")
    var myjob = new TestNativeJob
    myjob.compile("/tmp/TestInt.cpp")
    val intRdd = sc.parallelize(1 to 1000, 10)
    val res = myjob.apply(intRdd);
    val a = res.collect()
    a.map(i => println(i))
    sc.stop()
  }
}

