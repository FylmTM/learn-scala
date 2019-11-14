name := "scala-learn"
version := "0.1"
scalaVersion := "2.13.0"

lazy val rtjScalaBeginners = project in file("rock-the-jvm-scala-beginners")
lazy val rtjScalaAdvanced = (project settings(
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
)) in file("rock-the-jvm-scala-advanced")
lazy val projectGravitrips = project in file("project-gravitrips")
