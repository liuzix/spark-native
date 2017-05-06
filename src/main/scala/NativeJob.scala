package com.liuzix.SparkNative

import org.apache.spark.rdd.RDD

import scala.sys.process._
import scala.language.postfixOps
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.reflect.ClassTag

object NativeJob {
    var counter: Integer = 0
}


abstract class NativeJob[T: ClassTag] extends Serializable {
    
    private val identifier : String = NativeJob.counter.toString
    protected var binaryPath = ""
    NativeJob.counter += 1
    

    def compile (source: String) = {
        println(s"Preparing to compile $source to binary")
        binaryPath = s"/tmp/nativejob$identifier.so"
        val res = ((s"g++ -fPIC -shared $source  -o $binaryPath") !!)
        print (res)

    }
    
    def apply (dataSet: RDD[T]) : RDD[T] = {
        // Distribute the binary to nodes
        if (binaryPath == "") {
            throw new Exception ("No binary found")
        }
        val binaryData = Files.readAllBytes(Paths.get(binaryPath))
        val broadcastFile = dataSet.context.broadcast(binaryData)
        val res = dataSet.mapPartitionsWithIndex ((i,iter) => {
            try {
                Files.write(Paths.get(binaryPath), broadcastFile.value, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
                //(s"chmod +x /tmp/nativejob$identifier" !!)
                
            } catch {
                case e  : Throwable => throw new Exception ("Error writing binary to local filesystem")
            }
            applyPartition(i, iter)
        })
        res
    }

    def applyPartition (index: Integer, iter: Iterator[T]) : Iterator[T]


}