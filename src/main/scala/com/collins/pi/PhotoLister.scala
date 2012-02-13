package com.collins.pi

import org.apache.commons.io.DirectoryWalker
import java.io.File
import java.io.IOException
import java.util.Collection
import org.apache.commons.io.filefilter.FileFilterUtils
import java.util.ArrayList

import scala.collection.JavaConversions._

class PhotoLister extends DirectoryWalker[File](FileFilterUtils.suffixFileFilter(".jpg"), 9) {

   override def handleFile(file: File, depth: Int, results: Collection[File]) {
     results.add(file)
   }
   
   def listFiles(startDir: File): Seq[File] = {
     val result = new ArrayList[File]
     walk(startDir, result)
     
     result.toSeq
   }
}
