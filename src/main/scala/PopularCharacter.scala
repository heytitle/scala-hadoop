/**
  * Created by heytitle on 11/13/16.
  */

import java.io.File

import com.nicta.scoobi.Scoobi._
import Reduction._
import org.apache.commons.io.FileUtils


object PopularCharacter extends ScoobiApp {
  val input = "/Users/heytitle/projects/AIM3-Assignments_WS-2016-2017/Miscellaneous/MR_ExamplesHadoop/input/big.txt"
  val output = "/Users/heytitle/projects/scala-hadoop/output/PopularCharacter"


  def before(): Unit = {
    FileUtils.deleteDirectory(new File(output))
  }

  def run() {
    before()

    val lines = fromTextFile(input)

    val wordReg = "([a-z]{2,})".r

    val counts = lines.mapFlatten( _.toLowerCase.split(" ") )
        .map{ w =>
          wordReg.findFirstIn(w) match {
            case Some(c) => (c.substring(0,1),1)
            case _ => ("",0)
          }
        }
        .filter( _._2 > 0 )
        .groupByKey
        .combine(Sum.int)

    counts.map( w => w._1+","+w._2 ).toTextFile(output).persist

  }


}
