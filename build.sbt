lazy val root = (project in file("."))
  .settings(
    name := "github-issues-mover",
    description := "Move old issues on Github enterprise to new repository on different host's repository",
    version := "0.0.1",

    //scalaVersion := "0.1.1-bin-20170502-df22149-NIGHTLY",
    scalaVersion := "2.11.8",

    libraryDependencies ++= Seq(
      "com.typesafe.play" % "play-json_2.11" % "2.5.14",
      "org.scalaj" %% "scalaj-http" % "2.3.0"
    )
  )
  .enablePlugins(JavaAppPackaging)
