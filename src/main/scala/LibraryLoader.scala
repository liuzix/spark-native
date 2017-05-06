package com.liuzix.SparkNative

import com.sun.jna._

trait NativeLibrary[T] extends Library {
    def pushItem(item: T) : Unit
    def getResult(buf: Array[T], size: Int) : Unit
}

class NativeLibraryLoader[T] (path: String) {
    private var _lib: NativeLibrary[T] = null
    def call : NativeLibrary[T] = {
        if (_lib == null) {
            _lib = Native.loadLibrary(path, classOf[NativeLibrary[T]]).asInstanceOf[NativeLibrary[T]]
        } 
        _lib
    }
}
