package com.liuzix.SparkNative

import org.apache.log4j.{LogManager, Level}

object Holder extends Serializable {      
   @transient lazy val log = LogManager.getLogger("myLogger")
}

class TestNativeJob extends NativeJob[Int]{
    def applyPartition (index: Integer, iter: Iterator[Int]) : Iterator[Int] = {
        var lib = new NativeLibraryLoader[Int](this.binaryPath) 
        Holder.log.setLevel(Level.TRACE)
        val myList = iter.toArray
        myList.map( i => {
            //Holder.log.info(s"Pushing $i")
            lib.call.pushItem(i)
        })
        Holder.log.info("Pushed all data")
        var ret = new Array[Int](myList.size)
        lib.call.getResult(ret, myList.size)
        ret.iterator
    }
}