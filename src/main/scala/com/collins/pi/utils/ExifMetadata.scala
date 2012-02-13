package com.collins.pi.utils
import com.drew.metadata.Metadata
import java.io.File
import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifDirectory

class ExifMetadata(file: File) {
  
  def this(path: String) = this(new File(path))
  
  val m: Metadata = ImageMetadataReader.readMetadata(file)
  
  lazy val exifDir = m.getDirectory(classOf[ExifDirectory])
  
  lazy val datetime = exifDir.getDate(ExifDirectory.TAG_DATETIME)
  

}