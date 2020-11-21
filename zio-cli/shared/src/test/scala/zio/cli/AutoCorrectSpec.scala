package zio.cli

import zio.test.Assertion._
import zio.test._

object AutoCorrectSpec extends DefaultRunnableSpec {
  import AutoCorrect._

  def spec = suite("AutoCorrect.levensteinDistance Suite")(
    test("calculate the correct levenstein distance between two strings") {
      val opts = ParserOptions.default
      assert(levensteinDistance("", "", opts))(equalTo(0))
      assert(levensteinDistance("--force", "", opts))(equalTo(7))
      assert(levensteinDistance("", "--force", opts))(equalTo(7))
      assert(levensteinDistance("--force", "force", opts))(equalTo(2))
      assert(levensteinDistance("--force", "--forc", opts))(equalTo(1))
      assert(levensteinDistance("--force", "--Force", opts))(equalTo(1))
      assert(levensteinDistance("foo", "bar", opts))(equalTo(3))
    },
    test("takes into account the provided parserOptions case sensitivity") {
      val opts = ParserOptions(caseSensitive = false)
      assert(levensteinDistance("--force", "--force", opts))(equalTo(0))
      assert(levensteinDistance("--FORCE", "--force", opts))(equalTo(0))
    }
  )
}
