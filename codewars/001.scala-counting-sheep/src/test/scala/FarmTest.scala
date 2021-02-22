import org.scalatest.Fla
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FarmTest extends AnyFlatSpec with Matchers {

  val sheep = Array(
      true,  true,  true,  false,
      true,  true,  true,  true,
      true,  false, true,  false,
      true,  false, false, true,
      true,  true,  true,  true,
      false, false, true,  true
    )
  val sheepCount = 17

  s"countSheep(${sheep.mkString(", ")})" should s"return $sheepCount" in {
    Farm.countSheep(sheep) should be (sheepCount)
  }
}