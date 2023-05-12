package cl.vadnir.zbingoreload.External

import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import cl.vadnir.zbingoreload.ZBingoReload
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

class PlaceholderApi(private val plugin: ZBingoReload): PlaceholderExpansion() {


    override fun getIdentifier(): String {
        return "Bingo"
    }

    override fun getAuthor(): String {
        return "Vadnir"
    }

    override fun getVersion(): String {
        return "1.0"
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String? {

        when(params.lowercase()){
            "team" -> {
                val playerProfile = this.plugin.getPlayerManager().getPlayer(player!!.name!!)?: return ""

                if (playerProfile.getTeamId() == ""){
                    return ""
                }

                val team = this.plugin.getTeamManager().getTeam(playerProfile.getTeamId())?: return ""

                return team.getTeamNameDisplay()
            }

            "points" -> {
                val playerProfile = this.plugin.getPlayerManager().getPlayer(player!!.name!!)?: return "0"

                if (playerProfile.getTeamId() == ""){
                    return "0"
                }

                val team = this.plugin.getTeamManager().getTeam(playerProfile.getTeamId())?: return "0"

                return team.getPoints(
                    this.plugin.getAdvancementManager().getAllAdvancements()
                ).toString()
            }

            "color" -> {
                val playerProfile = this.plugin.getPlayerManager().getPlayer(player!!.name!!)?: return "#ffffff"

                if (playerProfile.getTeamId() == ""){
                    return "#ffffff"
                }

                val team = this.plugin.getTeamManager().getTeam(playerProfile.getTeamId())?: return "#ffffff"

                if (team.getTeamColor() == ""){
                    team.setTeamColor(this.plugin.colorUtils.getColor())
                }

                return team.getTeamColor()
            }

            "prefix" -> { //si el jugador esta solo -> "", si el jugador esta con mas jugadores -> "Equipo"

                val playerProfile = this.plugin.getPlayerManager().getPlayer(player!!.name!!)?: return ""

                if (playerProfile.getTeamId() == ""){
                    return ""
                }

                val team = this.plugin.getTeamManager().getTeam(playerProfile.getTeamId())?: return ""

                var prefix = ""

                if (team.getTeamColor() == ""){
                    team.setTeamColor(this.plugin.colorUtils.getColor())
                }

                prefix += "{${team.getTeamColor()}}" //{#ff00ff}

                if (team.getPlayerList().size > 1){
                    prefix += "[Equipo ${team.getTeamNameDisplay()}] " //{#ff00ff}[Equipo 1]
                    return prefix
                }

                return prefix
            }
        }

        if (params.contains("advancement:")) {

            val advancementName: String = params.split(":").last()?: return ""

            val playerProfile = this.plugin.getPlayerManager().getPlayer(player!!.name!!)?: return ""

            if (playerProfile.getTeamId() == ""){
                return ""
            }

            val team = this.plugin.getTeamManager().getTeam(playerProfile.getTeamId())?: return ""

            val advancementProfile: AdvancementProfile = this.plugin.getAdvancementManager().getAdvancement(
                advancementName
            )?: return ""

            if (team.hasAdvancement(advancementName)) {
                return advancementProfile.getUnicodeComplete()
            }
            return advancementProfile.getUnicodeNotComplete()
        }

        if (params.contains("partner:")) {

            val pos: String = params.split(":").last()?: return ""

            var index = 0

            index = try {
                pos.toInt()
            }catch (e: Exception){
                0
            }

            val playerProfile = this.plugin.getPlayerManager().getPlayer(player!!.name!!)?: return ""

            if (playerProfile.getTeamId() == ""){
                return ""
            }

            val team = this.plugin.getTeamManager().getTeam(playerProfile.getTeamId())?: return ""

            if (team.getPlayerList().size < index){
                return ""
            }

            val other = team.getPlayerList()[index] ?: return ""

            return other.getName()

        }

        return null
    }

}