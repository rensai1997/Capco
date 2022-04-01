package puzzle

class SudokuSpec extends TestSupport {

  "Sudoku" - {
    "validate" - {
      "should return VALID when given valid solution" in {
        new Sudoku(boardWithCorrectSolution).validate shouldBe Valid
      }

      forAll(incorrectSolutions) { (incorrectSolution, description) =>
        s"should return INVALID when given board with $description" in {
          new Sudoku(incorrectSolution).validate shouldBe Invalid
        }
      }

      forAll(incompleteBoard) { (incompleteBoard, description, errorMsg) =>
        s"should return INVALID when given board with $description" in {
          val exception = the[IllegalArgumentException] thrownBy new Sudoku(incompleteBoard).validate
          exception.getMessage should include(errorMsg)
        }
      }
    }
  }
}
