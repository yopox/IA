package report

import java.io.File

/**
 * Génère un rapport HTML du combat voulu.
 */

fun main(args: Array<String>) {
    HTMLBuilder.declareTeams(TeamBuilder.fighters)
    File("index.html").printWriter().use { out ->
        out.println(HTMLBuilder.getHTML())
    }
    print("done")
}