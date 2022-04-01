package example

import java.io.File

import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success, Try}

object Main extends LazyLogging {
  def main(args: Array[String]): Unit = {
    val maybeFile = args.headOption.map(fileName => Try(new File(fileName)))

    maybeFile.fold(logger.info(s"Usage validate.sh puzzleNAme.txt")) {
      case Success(file) =>
        val status = new Sudoku(fileToBoard(file)).validate
        logger.info(status.repr)
        System.exit(status.exitCode)
      case Failure(err) => logger.error(s"Error occurred when loading file: $err")
    }
  }

  private def fileToBoard(file: File): Board = {
    val source = io.Source.fromFile(file)
    val board = (for {
      line <- source.getLines()
      row = line.split(",").map(_.trim().toInt).toVector
    } yield row).toVector

    source.close()
    board
  }
}
