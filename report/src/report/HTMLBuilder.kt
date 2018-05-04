package report

import com.llgames.ia.battle.logic.LFighter

object HTMLBuilder {
    var code = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\" />\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <title>Fight Report</title>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"master.css\" />\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "    <p>PROJECT IA - FIGHT REPORT</p>\n" +
            "\n" +
            "    <div class=\"dotted\"></div>\n"

    fun declareTeams(fighters: Array<LFighter>) {
        code += "<p class=\"teamName\">TEAM #1</p>\n"

        for (fighter in fighters.filter { it.team == 0 }) {
            code += "<p class=\"perso\">${fighter.name} - ${fighter.job} - ${fighter.stats.hp}HP</p>\n" +
                    "    <p class=\"ia\">\n" +
                    "        ${fighter.getIAString().replace("\n", "<br>").dropLast(4)}\n" +
                    "   </p>\n" +
                    "</p>\n"
        }

        sep()

        code += "<p class=\"teamName\">TEAM #2</p>\n"

        for (fighter in fighters.filter { it.team == 1 }) {
            code += "<p class=\"perso\">${fighter.name} - ${fighter.job} - ${fighter.stats.hp}HP</p>\n" +
                    "    <p class=\"ia\">\n" +
                    "        ${fighter.getIAString().replace("\n", "<br>").dropLast(4)}\n" +
                    "   </p>\n" +
                    "</p>\n"
        }

    }

    fun sep() {
        code +="<div class=\"dotted\"></div>\n"
    }

    fun getHTML(): String {
        return "$code</body></html>"
    }
}