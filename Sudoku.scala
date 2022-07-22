package puzzle

import com.typesafe.scalalogging.LazyLogging

import scala.annotation.tailrec

class Sudoku(board: Board) extends LazyLogging {

  require(board.size == BoardSize, s"Board must have $BoardSize columns")
  require(board.forall(_.size == BoardSize), s"Each board row must have $BoardSize numbers")

  /**
    * Validates Sudoku board.
    *
    * This method takes a completed board and checks that the solution is valid using the steps below.
    * - each row is checked to contain no duplicates)
    * - each column is checked to contain no duplicates
    * - each mini square is checked to contain no duplicates
    *
    * As an optimization:
    * - the above checks are evaluated lazily and once a check fails, subsequent checks are skipped.
    * - the core checking function is tail recursive.
    *
    * When the board solution is valid, the message Valid is returned,
    * otherwise the message Invalid is returned and the duplicate cells are printed.
    *
    * The input board is validated to ensure that it has 9 x 9 cells each containing number 1 - 9
    */
  def validate: Status = {
    val linearRange = 0 until(end = BoardSize, step = 1)

    val miniSquareRange = for {
      i <- 0 until(end = BoardSize, step = MiniSquareSize)
      j <- 0 until(end = BoardSize, step = MiniSquareSize)
    } yield (i, j)

    lazy val validRows = linearRange.iterator
      .exists(row => check(row + 1, BoardSize, BoardSize, i = row)(fj = _ + 1) == Invalid)

    lazy val validColumns = linearRange.iterator
      .exists(column => check(BoardSize, column + 1, BoardSize, j = column)(fi = _ + 1) == Invalid)

    lazy val validMiniSquares = miniSquareRange.iterator.exists { case (row, column) =>
      val width = row + MiniSquareSize
      val height = column + MiniSquareSize
      check(width, height, MiniSquareSize, row, column)(fi = _ + 1) == Invalid
    }

    if (validRows || validColumns || validMiniSquares) Invalid else Valid
  }

  @tailrec
  private def check(x: Int, y: Int, gridSize: Int, i: Int = 0, j: Int = 0, usedNums: Map[Int, Cell] = Map.empty, status: Status = Valid)
                   (fi: Int => Int = identity, fj: Int => Int = identity): Status = {
    status match {
      case Invalid => status
      case Valid if i >= x && j >= y || usedNums.size == BoardSize => Valid
      case Valid if i < x && j >= y => check(x, y, gridSize, i + 1, j % y, usedNums)(fj = _ + 1)
      case Valid if i >= x && j < y => check(x, y, gridSize, i - gridSize, j + 1, usedNums)(fi = _ + 1)
      case Valid =>
        val number = board(i)(j)
        require(number >= 1 && number <= 9, s"Number $number at cell ($i, $j) is not between 1 and 9")
        val newStatus = usedNums.get(number).map { cell =>
          logger.info(s"Invalid: cell ($i, $j) has duplicated value ($number) with cell (${cell.x}, ${cell.y})")
          Invalid
        }.getOrElse(Valid)
        check(x, y, gridSize, fi(i), fj(j), usedNums + (number -> Cell(i, j)), newStatus)(fi, fj)
    }
  }
  def func(x:Int): Int = {
    x * 3
    }

}
