
import java.io.File
import scala.util.matching.Regex
import com.collins.pi.utils.PhotoImporter


object ImportApp extends App {

  val fromDir = args.first
  val toDir = args.array(1)
  
  val importer = new PhotoImporter(fromDir, toDir, Console.println)
  
  importer.importFiles()
  
}