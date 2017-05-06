import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.liuzix.SparkNative._


object Test {
  def main(args: Array[String]) {
    
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)
    var myjob = new TestNativeJob
    myjob.compile("/tmp/TestInt.cpp")
    val intRdd = sc.parallelize(1 to 1000, 10)
    val res = myjob.apply(intRdd);
    val a = res.collect()
    a.map(i => println(i))
    sc.stop()
  }
}

