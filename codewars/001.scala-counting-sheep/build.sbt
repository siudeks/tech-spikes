lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.13"
    )),
    name := "scalatest-example"
  )

libraryDependencies ++= {
  Seq(
    "org.scalatest" %% "scalatest" % "3.2.2" % Test
  )
}
