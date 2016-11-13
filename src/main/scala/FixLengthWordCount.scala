/**
  * Created by heytitle on 11/13/16.
  */

import java.io.File

import com.nicta.scoobi.Scoobi._
import Reduction._
import org.apache.commons.io.FileUtils


object FixLengthWordCount extends ScoobiApp {
  val input = "/Users/heytitle/projects/AIM3-Assignments_WS-2016-2017/Miscellaneous/MR_ExamplesHadoop/input/big.txt"
  val output = "/Users/heytitle/projects/scala-hadoop/output/FixLengthWordCount"

  val fixedLength = 5

  def before(): Unit = {
    FileUtils.deleteDirectory(new File(output))
  }

  def run() {
    before()

    val lines = fromTextFile(input)

    val counts = lines.mapFlatten( _.toLowerCase.split(" ") )
        .filter( w => w.length == fixedLength && w.matches(s"[a-z]{$fixedLength}") )
        .map( w => (w, 1) )
        .groupByKey
        .combine(Sum.int)

    counts.map( w => w._1+","+w._2 ).toTextFile(output).persist

  }


}
