package cl.vadnir.zbingoreload.External

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
                return this.plugin.getTeamManager().getTeam(
                    this.plugin.getPlayerManager().getPlayer(player!!.name!!)!!.getTeamId()?: return ""
                )!!.getTeamNameDisplay()
            }

            "points" -> {
                return this.plugin.getTeamManager().getTeam(
                    this.plugin.getPlayerManager().getPlayer(player!!.name!!)!!.getTeamId()?: return ""
                )!!.getPoints(this.plugin.getAdvancementManager().getAllAdvancements()).toString()
            }
        }

        if (params.contains("advancement:")) {

            val player = this.plugin.getPlayerManager().getPlayer(player!!.name!!)?: return ""

            Bukkit.getConsoleSender().sendMessage(player.getName())

            val team = this.plugin.getTeamManager().getTeam(player.getName())?: return ""

            val advancement = this.plugin.getAdvancementManager().getAllAdvancements().filter { it.getName() == params.split(":").last()}

            Bukkit.getConsoleSender().sendMessage(params.split(":").toString())
            Bukkit.getConsoleSender().sendMessage(params)

            if (advancement.isEmpty()){
                return "null"
            }

            return if ( team.hasAdvancement(advancement.first())){
                advancement.first().getUnicodeComplete()
            }else {
                advancement.first().getUnicodeNotComplete()
            }
        }

        return null
    }

}