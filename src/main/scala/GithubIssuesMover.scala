import play.api.libs.json._

import scalaj.http._

/**
  * Created by tanaka on 2017/05/14.
  */
object GithubIssuesMover {

  val usage: String =
    """
      |Usage: github-issues-mover --from --from-owner --from-repo --to --to-owner --to-repo --from-id --from-password --to-id --to-password
    """.stripMargin

  def main(args: Array[String]): Unit = {
    if (args.length != 24) {
      println(usage)
      sys.exit(0)
    }

    def nextOption(map: Map[String, String], list: List[String]): Map[String, String] = {
      def isSwitch(s: String) = s(0) == '-'
      list match {
        case Nil => map
        case "--from" :: value :: tail => nextOption(map ++ Map("from" -> value), tail)
        case "--from-owner" :: value :: tail => nextOption(map ++ Map("from-owner" -> value), tail)
        case "--from-repo" :: value :: tail => nextOption(map ++ Map("from-repo" -> value), tail)
        case "--to" :: value :: tail => nextOption(map ++ Map("to" -> value), tail)
        case "--to-owner" :: value :: tail => nextOption(map ++ Map("to-owner" -> value), tail)
        case "--to-repo" :: value :: tail => nextOption(map ++ Map("to-repo" -> value), tail)
        case "--id" :: value :: tail => nextOption(map ++ Map("id" -> value), tail)
        case "--password" :: value :: tail => nextOption(map ++ Map("password" -> value), tail)

        case option =>
          println("Unknown option " + option)
          sys.exit(1)
      }
    }

    val options = nextOption(Map(), args.toList)

    val response = Http(s"${options("from")}/repos/${options("from-owner")}/${options("from-repo")}/issues").auth(options("from-id"), options("from-password")).asString
    val responseJson = Json.parse(response.body).as[JsArray]
    responseJson.value.foreach { issue =>
      val path = (__ \ "assignee").json.prune
      val transformed = issue.transform(path)
      transformed.asOpt match {
        case Some(result) =>
          println("issue: " + issue.toString())
          val res = Http(s"${options("to")}/repos/${options("to-owner")}/${options("to-repo")}/issues").auth(options("to-id"), options("to-password")).postData(result.toString()).asString
          println("response status: " + res.statusLine)
          println("response body: " + res.body)
        case None => println("""Fail to prune "assignee"""")
      }
    }

    println("DONE")
  }

}
