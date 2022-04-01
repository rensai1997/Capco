package puzzle

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

trait TestSupport extends AnyFreeSpec with Matchers with TableDrivenPropertyChecks {
  val boardWithCorrectSolution: Vector[Vector[Int]] = Vector(
    Vector(1,2,3,4,5,6,7,8,9),
    Vector(1,2,3,4,5,6,7,8,9),
    Vector(1,2,3,4,5,6,7,8,9),
    Vector(1,2,3,4,5,6,7,8,9),
    Vector(1,2,3,4,5,6,7,8,9),
    Vector(1,2,3,4,5,6,7,8,9),
    Vector(1,2,3,4,5,6,7,8,9),
    Vector(1,2,3,4,5,6,7,8,9),
    Vector(1,2,3,4,5,6,7,8,9)
  )

  val boardWithIncorrectColumns: Vector[Vector[Int]] =
    (1 to BoardSize).toVector.map(Vector.fill(BoardSize)(_)) // all columns are the same
  val boardWithInsufficientRows: Vector[Vector[Int]] = Vector.fill(BoardSize)((1 until BoardSize).toVector)
  val boardWithInsufficientColumns: Vector[Vector[Int]] = Vector.fill(BoardSize - 1)((1 to BoardSize).toVector)
  val emptyBoard: Vector[Nothing] = Vector.empty
  val boardWithInvalidNumber: Vector[Vector[Int]] = Vector.fill(BoardSize)((2 to (BoardSize + 1)).toVector)

  val incorrectSolutions = Table(
    ("incorrectSolution", "description"),
    (boardWithIncorrectRows, "incorrect rows"),
    (boardWithIncorrectColumns, "incorrect columns"),
    (boardWithIncorrectFirstMiniSquare, "Incorrect first mini square"),
    (boardWithIncorrectLastMiniSquare, "Incorrect last mini square"),
    (boardWithIncorrectSolution, "Incorrect solution")
  )

  val incompleteBoard = Table(
    ("invalidBoard", "description", "errorMsg"),
    (emptyBoard, "no cells", "requirement failed: Board must have 9 columns"),
    (boardWithInsufficientRows, "insufficient rows", "requirement failed: Each board row must have 9 numbers"),
    (boardWithInsufficientColumns, "insufficient columns", "requirement failed: Board must have 9 columns"),
    (boardWithInvalidNumber, "number not between 1 - 9", "requirement failed: Number 10 at cell (0, 8) is not between 1 and 9")
  )
}
