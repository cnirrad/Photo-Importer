package com.collins.pi

import java.io.File
import java.text.SimpleDateFormat
import scala.annotation.implicitNotFound
import org.apache.commons.io.{FilenameUtils, FileUtils}
import com.collins.pi.utils.ExifMetadata


abstract class FilenameResult
case class TrueDuplicate extends FilenameResult
case class NameDuplicate extends FilenameResult
case class ValidFilename(f: File) extends FilenameResult

class PhotoImporter(fromDir: String, toDir: String, logger: String => Unit) {

  def importFiles(): Unit = {
    val walker = new PhotoLister
    val files = walker.listFiles(new File(fromDir))
    
    files.par.foreach(performMove)
  }
  
  def nameFile(f: File, em: ExifMetadata, seq: Int): FilenameResult = {
    val (dir, filename) = renamePhoto(f, em, seq)

    val destDir = new File(toDir + "/" + dir)
    val dest = new File(destDir, filename)

    if (dest.exists()) {
      //
      // Determine if this is a true duplicate
      //
      if (contentEquals(f, dest)) {
        logger(f.getName() + " is a duplicate.")
        return TrueDuplicate()
      } else {
        return NameDuplicate()
      }
    }
    return ValidFilename(dest)
  }

  def performMove(f: File): Unit = {
    val em = new ExifMetadata(f)
    var seq = 1
    while (seq < 10) {
      nameFile(f, em, seq) match {
        case NameDuplicate() =>
          seq += 1
          logger(f.getName() + " is a duplicate name. Trying with seq #" + seq)
        case ValidFilename(dest) =>
          FileUtils.copyFile(f, dest)
          logger(f.getName() + " copied to " + dest.getAbsolutePath())
          return
        case TrueDuplicate() =>
          return
      }
    }
  }
  
  def renamePhoto(f: File, em: ExifMetadata, seqNum: Int): (String, String) = {
    val df = new SimpleDateFormat("yyyyMMdd-HHmmss")

    val directory = new SimpleDateFormat("yyyy-MM").format(em.datetime)

    var filename = df.format(em.datetime) 
    if (seqNum > 1) {
      filename += "-" + seqNum.toString()
    } 

    filename += "." + FilenameUtils.getExtension(f.getName())
    
    return (directory, filename)
  }
  
  def contentEquals(f1: File, f2: File): Boolean = {
    val chk1 = FileUtils.checksum(f1, new java.util.zip.Adler32())
    val chk2 = FileUtils.checksum(f2, new java.util.zip.Adler32())
    
    return chk1.getValue() == chk2.getValue()
  }
}