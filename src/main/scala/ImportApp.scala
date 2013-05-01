
import java.io.File
import scala.util.matching.Regex
import com.collins.pi.PhotoImporter

object ImportApp extends App {

  if (args.length >= 2) {
    doImport()
  } else {
    println("Not enough arguements found.")
    println("expected fromDir and toDir")
  }

  def doImport(): Unit = {
    val fromDir = args.head
    val toDir = args(1)

    val importer = new PhotoImporter(fromDir, toDir, Console.println)

    importer.importFiles()
  }

}