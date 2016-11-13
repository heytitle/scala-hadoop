name := "MyApplication"

version := "1.0"

scalaVersion := "2.10.3"

fork := true

libraryDependencies += "com.nicta" %% "scoobi" % "0.9.2"

resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
                  Resolver.sonatypeRepo("snaspshots"))