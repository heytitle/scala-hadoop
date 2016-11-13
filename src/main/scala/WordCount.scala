/**
  * Created by heytitle on 11/13/16.
  */

import com.nicta.scoobi.Scoobi._
import Reduction._
import org.apache.commons.io.FileUtils
import java.io.File



object WordCount extends ScoobiApp {
  val input = "/Users/heytitle/projects/AIM3-Assignments_WS-2016-2017/Miscellaneous/MR_ExamplesHadoop/input/Word_Count_input.txt"
  val output = "/Users/heytitle/projects/scala-hadoop/output/Word_Count"

  def before(): Unit = {
    FileUtils.deleteDirectory(new File(output))
  }

  def run() {
    before()

    val lines = fromTextFile(input)

    val counts = lines.mapFlatten(_.replace("\"","").split(" "))
      .map(word => (word, 1))
      .groupByKey
      .combine(Sum.int)

    counts.map[String]( w => w._1 )
      .toTextFile(output).persist
  }


}
