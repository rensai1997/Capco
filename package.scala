package object puzzle {

  val BoardSize = 9
  val MiniSquareSize = 3
  type Board = Vector[Vector[Int]]
  final case class Cell(x: Int, y: Int)

  sealed trait Status {
    def repr: String
    def exitCode: Int
  }

  final case object Valid extends Status {
    override val repr: String = "VALID"

    override def exitCode: Int = 0
  }

  final case object Invalid extends Status {
    override val repr: String = "INVALID"

    override def exitCode: Int = 1
  }
}
